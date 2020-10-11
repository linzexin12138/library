package com.wteam.modules.library.service.impl;

import com.wteam.modules.library.domain.OrderTime;
import com.wteam.modules.library.domain.criteria.OrderTimeQueryCriteria;
import com.wteam.modules.library.domain.dto.OrderTimeDTO;
import com.wteam.modules.library.domain.mapper.OrderTimeMapper;
import com.wteam.modules.library.repository.OrderTimeRepository;
import com.wteam.modules.library.service.OrderTimeService;
import com.wteam.utils.PageUtil;
import com.wteam.utils.QueryHelper;
import com.wteam.utils.RedisUtils;
import com.wteam.utils.ValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/22 20:07
 */

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "orderTime")
@Transactional( readOnly = true, rollbackFor = Exception.class)
public class OrderTimeServiceImpl implements OrderTimeService {

    private final OrderTimeRepository orderTimeRepository;
    private final OrderTimeMapper orderTimeMapper;
    private final RedisUtils redisUtils;

    @Override
    @Cacheable(key = "'id:'+#p0")
    public OrderTime findById(Long id) {
        return orderTimeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderTime create(OrderTime resources) {
        return orderTimeRepository.save(resources);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void update(OrderTime resources) {
        OrderTime orderTime = orderTimeRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull(orderTime, OrderTime.ENTITY_NAME,"id",resources.getId());
        assert orderTime != null;
        orderTime.setName(resources.getName());
        orderTime.setStarTime(resources.getStarTime());
        orderTime.setEndTime(resources.getEndTime());
        orderTimeRepository.save(orderTime);
        redisUtils.del("orderTime::getSomedayOrderTime:*");
    }

    @Override
    public void deleteAll(Set<Long> ids) {
        orderTimeRepository.logicDeleteInBatchById(ids);
        redisUtils.delByKeys("orderTime::id:",ids);
        redisUtils.del("orderTime::getSomedayOrderTime:*");
    }

    @Override
    public List<OrderTime> queryAll() {
        return orderTimeRepository.findAll();
    }

    @Override
    public Map<String,Object> queryAll(OrderTimeQueryCriteria criteria, Pageable pageable){
        Page<OrderTime> page = orderTimeRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(orderTimeMapper::toDto));
    }

    @Override
    public List<OrderTimeDTO> queryAll(OrderTimeQueryCriteria criteria){
        return orderTimeMapper.toDto(orderTimeRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "'getSomedayOrderTime:' + #p0")
    public List<OrderTimeDTO> getSomedayOrderTime(LocalDate localDate) {
        OrderTimeQueryCriteria criteria = new OrderTimeQueryCriteria();

        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
            criteria.setName("周末");
        }else{
            criteria.setName("工作日");
        }

        return queryAll(criteria);
    }
}
