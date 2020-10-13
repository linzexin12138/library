/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.service.impl;


import com.wteam.modules.library.service.CreditScoreLogService;
import com.wteam.modules.library.domain.CreditScoreLog;
import com.wteam.modules.library.domain.dto.CreditScoreLogDTO;
import com.wteam.modules.library.domain.criteria.CreditScoreLogQueryCriteria;
import com.wteam.modules.library.domain.mapper.CreditScoreLogMapper;
import com.wteam.modules.library.repository.CreditScoreLogRepository;
import com.wteam.exception.BadRequestException;
import com.wteam.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.*;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
* 信用分记录 业务实现层.
* @author charles
* @since 2020-10-03
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "creditScoreLog")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CreditScoreLogServiceImpl implements CreditScoreLogService {

    private final CreditScoreLogRepository creditScoreLogRepository;

    private final CreditScoreLogMapper creditScoreLogMapper;

    private final RedisUtils redisUtils;

    @Override
    public Map<String,Object> queryAll(CreditScoreLogQueryCriteria criteria, Pageable pageable){
        Page<CreditScoreLog> page = creditScoreLogRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(creditScoreLogMapper::toDto));
    }

    @Override
    public List<CreditScoreLogDTO> queryAll(CreditScoreLogQueryCriteria criteria){
        return creditScoreLogMapper.toDto(creditScoreLogRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "'id:'+#p0")
    public CreditScoreLogDTO findDTOById(Long id) {
        CreditScoreLog creditScoreLog = creditScoreLogRepository.findById(id).orElse(null);
        ValidUtil.notNull(creditScoreLog,CreditScoreLog.ENTITY_NAME,"id",id);
        return creditScoreLogMapper.toDto(creditScoreLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreditScoreLogDTO create(CreditScoreLog resources) {
        return creditScoreLogMapper.toDto(creditScoreLogRepository.save(resources));
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void update(CreditScoreLog resources) {
        CreditScoreLog creditScoreLog = creditScoreLogRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull( creditScoreLog,CreditScoreLog.ENTITY_NAME,"id",resources.getId());

        creditScoreLog.copy(resources);
        creditScoreLogRepository.save(creditScoreLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Set<Long> ids) {
        redisUtils.delByKeys("creditScoreLog::id:",ids);
        creditScoreLogRepository.logicDeleteInBatchById(ids);
    }

    @Override
    public void download(List<CreditScoreLogDTO> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CreditScoreLogDTO creditScoreLog : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("创建时间", creditScoreLog.getCreatedAt());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}