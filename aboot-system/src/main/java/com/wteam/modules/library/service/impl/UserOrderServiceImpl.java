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
import com.wteam.modules.library.domain.OrderRecord;
import com.wteam.modules.library.domain.vo.OrderRecordVO;
import com.wteam.modules.library.service.UserOrderService;
import com.wteam.modules.library.domain.UserOrder;
import com.wteam.modules.library.domain.dto.UserOrderDTO;
import com.wteam.modules.library.domain.criteria.UserOrderQueryCriteria;
import com.wteam.modules.library.domain.mapper.UserOrderMapper;
import com.wteam.modules.library.repository.UserOrderRepository;
import com.wteam.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.*;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
* 用户预约信息 业务实现层.
* @author charles
* @since 2020-10-02
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "userOrder")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserOrderServiceImpl implements UserOrderService {

    private final UserOrderRepository userOrderRepository;

    private final UserOrderMapper userOrderMapper;

    private final RedisUtils redisUtils;

    @Override
    public Map<String,Object> queryAll(UserOrderQueryCriteria criteria, Pageable pageable){
        Page<UserOrder> page = userOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userOrderMapper::toDto));
    }

    @Override
    public List<UserOrderDTO> queryAll(UserOrderQueryCriteria criteria){
        return userOrderMapper.toDto(userOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "'id:'+#p0")
    public UserOrderDTO findDTOById(Long id) {
        UserOrder userOrder = userOrderRepository.findById(id).orElse(null);
        ValidUtil.notNull(userOrder,UserOrder.ENTITY_NAME,"id",id);
        return userOrderMapper.toDto(userOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserOrderDTO create(UserOrder resources) {
        return userOrderMapper.toDto(userOrderRepository.save(resources));
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void update(UserOrder resources) {
        UserOrder userOrder = userOrderRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull( userOrder,UserOrder.ENTITY_NAME,"id",resources.getId());
        userOrder.copy(resources);
        userOrderRepository.save(userOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Set<Long> ids) {
        redisUtils.delByKeys("userOrder::id:",ids);
        userOrderRepository.logicDeleteInBatchById(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByOrderId(Long orderId) {
        UserOrder userOrder = userOrderRepository.findByOrderId(orderId);
        redisUtils.del("userOrder::id:"+ userOrder.getId());
        userOrderRepository.deleteByOrderId(userOrder.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHistory() {
        userOrderRepository.deleteAllByDateBefore(LocalDate.now());
    }

    @Override
    public void download(List<UserOrderDTO> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserOrderDTO userOrder : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public boolean checkRepeat(Long userId, LocalDate date, List<Long> orderTimeIdList) {
        if (userOrderRepository.checkRepeat(userId, date, orderTimeIdList) > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void create(List<OrderRecord> orderRecordList) {
        UserOrder userOrder = null;
        for (OrderRecord orderRecord : orderRecordList){
            userOrder = new UserOrder();
            userOrder.setDate(orderRecord.getDate());
            userOrder.setUserId(orderRecord.getUserId());
            userOrder.setOrderTimeId(orderRecord.getOrderTime().getId());
            userOrder.setSeatId(orderRecord.getSeatId());
            userOrder.setOrderId(orderRecord.getId());
            try {
                userOrderRepository.save(userOrder);
            }catch (Exception e){
                throw new BadRequestException("已经被其他人预约了");
            }
        }
    }

}