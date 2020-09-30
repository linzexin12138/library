/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.service.impl;


import com.wteam.modules.library.domain.vo.OrderRecordVO;
import com.wteam.modules.library.service.OrderRecordService;
import com.wteam.modules.library.domain.OrderRecord;
import com.wteam.modules.library.domain.dto.OrderRecordDTO;
import com.wteam.modules.library.domain.criteria.OrderRecordQueryCriteria;
import com.wteam.modules.library.domain.mapper.OrderRecordMapper;
import com.wteam.modules.library.repository.OrderRecordRepository;
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
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
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
    @Cacheable(key = "'id:'+#p0")
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
    @Transactional(rollbackFor = Exception.class, timeout = 30)
    public void create(OrderRecordVO orderRecordVO) {

        StringBuilder stringBuilder = new StringBuilder("INSERT INTO order_record (date,order_time_id,seat_id,user_id) VALUES ");
        @NotNull Timestamp date = orderRecordVO.getDate();
        @NotNull Long seatId = orderRecordVO.getSeatId();
        Long userId = orderRecordVO.getUserId();
        List<Long> orderTimeIdList = orderRecordVO.getOrderTimeIdList();
        for (Long orderTimeId : orderTimeIdList){
            stringBuilder.append("('" + date + "'," + orderTimeId + "," + seatId + "," + userId + "),");
        }
        String sql = stringBuilder.substring(0, stringBuilder.length() - 1);
        jdbcTemplate.execute(sql);
    }



    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void update(OrderRecord resources) {
        OrderRecord orderRecord = orderRecordRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull( orderRecord,OrderRecord.ENTITY_NAME,"id",resources.getId());

        orderRecord.copy(resources);
        orderRecordRepository.save(orderRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Set<Long> ids) {
        redisUtils.delByKeys("orderRecord::id:",ids);
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
    public void cancelOrder(OrderRecord resource) {
        OrderRecord orderRecord = orderRecordRepository.findByDateAndOrderTimeIdAndSeatIdAndUserId(resource.getDate(), resource.getOrderTimeId(), resource.getSeatId(), resource.getUserId());
        orderRecord.setStatus(4);
        orderRecordRepository.save(orderRecord);
    }


}