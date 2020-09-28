package com.wteam.modules.library.service;

import com.wteam.modules.library.domain.LibOrderTime;

import java.util.List;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/22 20:06
 */
public interface LibOrderTimeService {

    LibOrderTime findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    LibOrderTime create(LibOrderTime resources);

    /**
     * update
     * @param resources
     */
    void update(LibOrderTime resources);

    /**
     * delete
     * @param ids
     */
    void delete(Set<Long> ids);


    /**
     * queryAll
     * @return
     */
    List<LibOrderTime> queryAll();

}
