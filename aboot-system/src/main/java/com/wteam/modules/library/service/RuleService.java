package com.wteam.modules.library.service;

import com.wteam.modules.library.domain.Rule;

import java.util.List;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/21 10:54
 */
public interface RuleService {
    /**
     * findById
     * @param name
     * @return
     */

    List<Rule> findRuleByName(String name);

    /**
     * findAll
     * @return
     */
    List<Rule> findAll();

    /**
     * create
     * @param resources
     * @return
     */
    Rule create(Rule resources);

    /**
     * update
     * @param resources
     */
    void update(Rule resources);

    /**
     * delete
     * @param ids
     */
    void delete(Set<Long> ids);
    
}
