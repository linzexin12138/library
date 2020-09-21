package com.wteam.modules.library.service;

import com.wteam.modules.library.domain.Card;

import java.util.List;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/21 10:54
 */
public interface CardService {
    /**
     * findById
     * @param name
     * @return
     */

    List<Card> findCardByName(String name);

    /**
     * findAll
     * @return
     */
    List<Card> findAll();

    /**
     * create
     * @param resources
     * @return
     */
    Card create(Card resources);

    /**
     * update
     * @param resources
     */
    void update(Card resources);

    /**
     * delete
     * @param ids
     */
    void delete(Set<Long> ids);
    
}
