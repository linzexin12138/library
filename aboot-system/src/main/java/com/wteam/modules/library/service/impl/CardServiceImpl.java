package com.wteam.modules.library.service.impl;

import com.wteam.modules.library.domain.Card;
import com.wteam.modules.library.repository.CardRepository;
import com.wteam.modules.library.service.CardService;
import com.wteam.utils.RedisUtils;
import com.wteam.utils.ValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
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
@CacheConfig(cacheNames = "card")
@Transactional( readOnly = true, rollbackFor = Exception.class)
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final RedisUtils redisUtils;


    @Override
    public List<Card> findCardByName(String name) {
        return cardRepository.findAllByName(name);
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Card create(Card resources) {
         return cardRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Card resources) {
        Card card = cardRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull(card,Card.ENTITY_NAME,"id",resources.getId());
        assert card != null;
        card.setName(resources.getName());
        card.setContent(resources.getContent());
        cardRepository.save(card);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            cardRepository.logicDelete(id);
        }
    }

}
