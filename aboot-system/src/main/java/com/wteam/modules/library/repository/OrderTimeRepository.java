package com.wteam.modules.library.repository;

import com.wteam.base.BaseRepository;
import com.wteam.modules.library.domain.OrderTime;

import java.util.List;

/**
 * @Author: Charles
 * @Date: 2020/9/22 20:09
 */
public interface OrderTimeRepository extends BaseRepository<OrderTime,Long> {
    List<OrderTime> findAll();

}
