package com.wteam.modules.library.service.impl;

import com.wteam.modules.library.domain.Rule;
import com.wteam.modules.library.repository.RuleRepository;
import com.wteam.modules.library.service.RuleService;
import com.wteam.utils.RedisUtils;
import com.wteam.utils.ValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/21 11:02
 */

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "rule")
@Transactional( readOnly = true, rollbackFor = Exception.class)
public class RuleServiceImpl implements RuleService {

    private final RuleRepository ruleRepository;
    private final RedisUtils redisUtils;


    @Override
    public List<Rule> findRuleByName(String name) {
        return ruleRepository.findAllByName(name);
    }

    @Override
    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Rule create(Rule resources) {
         return ruleRepository.save(resources);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void update(Rule resources) {
        Rule rule = ruleRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull(rule,Rule.ENTITY_NAME,"id",resources.getId());
        assert rule != null;
        rule.setName(resources.getName());
        rule.setContent(resources.getContent());
        ruleRepository.save(rule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        redisUtils.delByKeys("rule::id:",ids);
        ruleRepository.logicDeleteInBatchById(ids);
    }

}
