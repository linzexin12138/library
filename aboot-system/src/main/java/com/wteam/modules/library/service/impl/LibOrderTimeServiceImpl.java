package com.wteam.modules.library.service.impl;

import com.wteam.modules.library.domain.LibOrderTime;
import com.wteam.modules.library.repository.LibOrderTimeRepository;
import com.wteam.modules.library.service.LibOrderTimeService;
import com.wteam.utils.ValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/22 20:07
 */

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "libOrderTime")
@Transactional( readOnly = true, rollbackFor = Exception.class)
public class LibOrderTimeServiceImpl implements LibOrderTimeService {

    private final LibOrderTimeRepository libOrderTimeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LibOrderTime create(LibOrderTime resources) {
        return libOrderTimeRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LibOrderTime resources) {
        LibOrderTime libOrderTime = libOrderTimeRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull(libOrderTime,LibOrderTime.ENTITY_NAME,"id",resources.getId());
        assert libOrderTime != null;
        libOrderTime.setName(resources.getName());
        libOrderTimeRepository.save(libOrderTime);
    }

    @Override
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            libOrderTimeRepository.logicDelete(id);
        }
    }

    @Override
    public List<LibOrderTime> queryAll() {
        return libOrderTimeRepository.findAll();
    }
}
