package com.wteam.modules.library.repository;

import com.wteam.base.BaseRepository;
import com.wteam.modules.library.domain.LibOrderTime;

import java.util.List;

/**
 * @Author: Charles
 * @Date: 2020/9/22 20:09
 */
public interface LibOrderTimeRepository extends BaseRepository<LibOrderTime,Long> {
    List<LibOrderTime> findAll();

}
