package com.wteam.modules.library.service;

import com.wteam.modules.library.domain.OrderTime;
import com.wteam.modules.library.domain.criteria.OrderTimeQueryCriteria;
import com.wteam.modules.library.domain.dto.OrderTimeDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/22 20:06
 */
public interface OrderTimeService {

    OrderTime findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    OrderTime create(OrderTime resources);

    /**
     * update
     * @param resources
     */
    void update(OrderTime resources);

    /**
     * delete
     * @param ids
     */
    void delete(Set<Long> ids);


    /**
     * queryAll
     * @return
     */
    List<OrderTime> queryAll();

    Map<String,Object> queryAll(OrderTimeQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     * @param criteria
     * @return
     */
    List<OrderTimeDTO> queryAll(OrderTimeQueryCriteria criteria);

}
