package com.wteam.modules.library.repository;

import com.wteam.base.BaseRepository;
import com.wteam.modules.library.domain.Card;
import com.wteam.modules.library.domain.Seat;

import java.util.List;

/**
 * @Author: Charles
 * @Date: 2020/9/21 14:56
 */
public interface CardRepository extends BaseRepository<Card, Long> {

    List<Card> findAllByName(String name);
}
