/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.service.impl;


import com.wteam.exception.BadRequestException;
import com.wteam.modules.library.domain.OrderTime;
import com.wteam.modules.library.domain.vo.OrderRecordVO;
import com.wteam.modules.library.service.OrderRecordService;
import com.wteam.modules.library.domain.OrderRecord;
import com.wteam.modules.library.domain.dto.OrderRecordDTO;
import com.wteam.modules.library.domain.criteria.OrderRecordQueryCriteria;
import com.wteam.modules.library.domain.mapper.OrderRecordMapper;
import com.wteam.modules.library.repository.OrderRecordRepository;
import com.wteam.modules.library.service.OrderTimeService;
import com.wteam.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.*;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
* 预约记录 业务实现层.
* @author charles
* @since 2020-09-23
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "orderRecord")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class OrderRecordServiceImpl implements OrderRecordService {

    private final OrderRecordRepository orderRecordRepository;

    private final OrderTimeService orderTimeService;

    private final OrderRecordMapper orderRecordMapper;

    private final RedisUtils redisUtils;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<String,Object> queryAll(OrderRecordQueryCriteria criteria, Pageable pageable){
        Page<OrderRecord> page = orderRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(orderRecordMapper::toDto));
    }

    @Override
    public List<OrderRecordDTO> queryAll(OrderRecordQueryCriteria criteria){
        return orderRecordMapper.toDto(orderRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "'id:'+#p0")
    public OrderRecordDTO findDTOById(Long id) {
        OrderRecord orderRecord = orderRecordRepository.findById(id).orElse(null);
        ValidUtil.notNull(orderRecord,OrderRecord.ENTITY_NAME,"id",id);
        return orderRecordMapper.toDto(orderRecord);
    }

    @Override
    @Cacheable(key = "'findById:'+#p0")
    public OrderRecord findById(Long id) {
        OrderRecord orderRecord = orderRecordRepository.findById(id).orElse(null);
        ValidUtil.notNull(orderRecord,OrderRecord.ENTITY_NAME,"id",id);
        return orderRecord;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    public OrderRecordDTO create(OrderRecord resources) {

        OrderRecord orderRecord = orderRecordRepository.save(resources);
        return orderRecordMapper.toDto(orderRecord);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<OrderRecord> create(OrderRecordVO orderRecordVO) {

        LocalDate date = orderRecordVO.getDate();
        Long userId = orderRecordVO.getUserId();
        List<Long> seatIdList = orderRecordVO.getSeatIdList();
        List<Long> orderTimeIdList = orderRecordVO.getOrderTimeIdList();

        List<OrderRecord> list = new ArrayList<>();
        OrderRecord orderRecord = null;
        for (int i = 0; i < orderTimeIdList.size(); i++){
            Long orderTimeId = orderTimeIdList.get(i);
            OrderTime orderTime = orderTimeService.findById(orderTimeId);
            LocalDateTime dateTime = LocalDateTime.of(date, orderTime.getEndTime());
            if (dateTime.isBefore(LocalDateTime.now())){
                String start = orderTime.getStarTime().toString();
                String end = orderTime.getEndTime().toString();
                throw new BadRequestException("该时间段 " + start + " - " + end + "已不可预约");
            }
            Long seatId = seatIdList.get(i);
            orderRecord = new OrderRecord();
            orderRecord.setDate(date);
            orderRecord.setUserId(userId);
            orderRecord.setSeatId(seatId);
            orderRecord.setOrderTime(orderTime);
            orderRecord.setStatus(0);
            list.add(orderRecord);
        }

        return orderRecordRepository.saveAll(list);

//        StringBuilder stringBuilder = new StringBuilder("INSERT INTO order_record (date,order_time_id,seat_id,user_id) VALUES ");

//        for (int i = 0; i < orderTimeIdList.size(); i++){
//            Long orderTimeId = orderTimeIdList.get(i);
//            Long seatId = seatIdList.get(i);
//            stringBuilder.append("('" + date + "'," + orderTimeId + "," + seatId + "," + userId + "),");
//        }
//        String sql = stringBuilder.substring(0, stringBuilder.length() - 1);
//        jdbcTemplate.execute(sql);
    }



    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void update(OrderRecord resources) {
        OrderRecord orderRecord = orderRecordRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull( orderRecord,OrderRecord.ENTITY_NAME,"id",resources.getId());
        orderRecord.copy(resources);
        orderRecordRepository.save(orderRecord);
        redisUtils.del("orderRecord::findById:" + orderRecord.getId());
        redisUtils.del("orderRecord::findByIdAndUserId:" + orderRecord.getId() + orderRecord.getUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Set<Long> ids) {
        redisUtils.delByKeys("orderRecord::id:",ids);
        redisUtils.delByKeys("orderRecord::findById:",ids);

        orderRecordRepository.logicDeleteInBatchById(ids);
    }

    @Override
    public void download(List<OrderRecordDTO> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderRecordDTO orderRecord : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("预约日期", orderRecord.getDate());
            map.put("预约时间段id", orderRecord.getOrderTimeId());
            map.put("用户id", orderRecord.getUserId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(OrderRecord orderRecord) {
        redisUtils.del("orderRecord::findById:" + orderRecord.getId());
        redisUtils.del("orderRecord::findByIdAndUserId:" + orderRecord.getId() + orderRecord.getUserId());
        orderRecordRepository.save(orderRecord);
    }

    @Override
    @Cacheable(key = "'findByIdAndUserId:'+#p0 +#p1")
    public OrderRecord findByIdAndUserId(Long orderRecordId, Long userId) {
        return orderRecordRepository.findByIdAndUserId(orderRecordId, userId);
    }

  }