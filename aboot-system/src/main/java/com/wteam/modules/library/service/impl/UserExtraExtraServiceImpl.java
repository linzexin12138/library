/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.service.impl;


import com.wteam.modules.library.domain.UserExtra;
import com.wteam.modules.library.service.UserExtraService;
import com.wteam.modules.library.domain.dto.UserExtraDTO;
import com.wteam.modules.library.domain.criteria.UserExtraQueryCriteria;
import com.wteam.modules.library.domain.mapper.UserExtraMapper;
import com.wteam.modules.library.repository.UserExtraRepository;
import com.wteam.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.*;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
* 图书馆用户 业务实现层.
* @author charles
* @since 2020-09-23
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "userExtra")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserExtraExtraServiceImpl implements UserExtraService {

    private final UserExtraRepository userExtraRepository;

    private final UserExtraMapper userExtraMapper;

    private final RedisUtils redisUtils;

    @Override
    public Map<String,Object> queryAll(UserExtraQueryCriteria criteria, Pageable pageable){
        Page<UserExtra> page = userExtraRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userExtraMapper::toDto));
    }

    @Override
    public List<UserExtraDTO> queryAll(UserExtraQueryCriteria criteria){
        return userExtraMapper.toDto(userExtraRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "'id:'+#p0")
    public UserExtraDTO findDTOById(Long id) {
        UserExtra userExtra = userExtraRepository.findById(id).orElse(null);
        ValidUtil.notNull(userExtra, UserExtra.ENTITY_NAME,"id",id);
        return userExtraMapper.toDto(userExtra);
    }

    @Override
    @Cacheable(key = "'findByUserId:'+#p0")
    public UserExtra findByUserId(Long userId) {
        UserExtra userExtra = userExtraRepository.findByUserId(userId).orElse(null);
        ValidUtil.notNull(userExtra, UserExtra.ENTITY_NAME,"id",userId);
        return userExtra;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserExtraDTO create(UserExtra resources) {
        return userExtraMapper.toDto(userExtraRepository.save(resources));
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void update(UserExtra resources) {
        UserExtra userExtra = userExtraRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull(userExtra, UserExtra.ENTITY_NAME,"id",resources.getId());
        userExtra.copy(resources);
        userExtraRepository.save(userExtra);
        redisUtils.del("userExtra::findByUserId:" + userExtra.getUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Set<Long> ids, Set<Long> userIds) {
        redisUtils.delByKeys("userExtra::id:",ids);
        redisUtils.delByKeys("userExtra::findByUserId:",userIds);
        userExtraRepository.logicDeleteInBatchById(ids);
    }


    @Override
    public void download(List<UserExtraDTO> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserExtraDTO userExtra : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("信用分", userExtra.getCreditScore());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "'id:' + #p0.id")
    public void updateCreditScore(UserExtra userExtra) {
        userExtraRepository.save(userExtra);
        redisUtils.del("userExtra::findByUserId:" + userExtra.getUserId());
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void resetSignInFlag() {
        userExtraRepository.resetSignInFlag();
    }
}