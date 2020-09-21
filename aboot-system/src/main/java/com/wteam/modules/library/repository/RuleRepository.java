package com.wteam.modules.library.repository;

import com.wteam.base.BaseRepository;
import com.wteam.modules.library.domain.Rule;
import com.wteam.modules.library.domain.Seat;

import java.util.List;

/**
 * @Author: Charles
 * @Date: 2020/9/21 14:56
 */
public interface RuleRepository extends BaseRepository<Rule, Long> {

    List<Rule> findAllByName(String name);
}
