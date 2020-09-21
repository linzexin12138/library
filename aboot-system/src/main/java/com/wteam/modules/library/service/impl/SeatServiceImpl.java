package com.wteam.modules.library.service.impl;

import com.wteam.modules.library.domain.Seat;
import com.wteam.modules.library.domain.criteria.SeatQueryCriteria;
import com.wteam.modules.library.domain.dto.SeatDTO;
import com.wteam.modules.library.domain.dto.SeatDTO;
import com.wteam.modules.library.domain.mapper.SeatMapper;
import com.wteam.modules.library.repository.SeatRepository;
import com.wteam.modules.library.service.SeatService;
import com.wteam.modules.library.service.SeatService;
import com.wteam.utils.QueryHelper;
import com.wteam.utils.RedisUtils;
import com.wteam.utils.ValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/21 11:02
 */

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "seat")
@Transactional( readOnly = true, rollbackFor = Exception.class)
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    private final SeatMapper seatMapper;

    private final RedisUtils redisUtils;


    @Override
    @Cacheable(key = "'seat:' + #p0")
    public SeatDTO findDTOById(Long id) {

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SeatDTO create(Seat resources) {
        Seat seat = seatRepository.save(resources);
        return seatMapper.toDto(seat);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Seat resources) {
        Seat seat = seatRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull(seat,Seat.ENTITY_NAME,"id",resources.getId());
        assert seat != null;
        seat.setName(resources.getName());
        seat.setStatus(resources.getStatus());
        seatRepository.save(seat);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            seatRepository.logicDelete(id);
        }
    }

    @Override
    public Map<String, Object> queryAll(SeatQueryCriteria criteria, Pageable pageable) {
        return null;
    }

    @Override
    public List<SeatDTO> queryAll(SeatQueryCriteria criteria) {
        return  seatMapper.toDto(seatRepository.findAll((root, cq, cb) -> QueryHelper.andPredicate(root, criteria, cb)));

    }

    @Override
    public void download(List<SeatDTO> queryAll, HttpServletResponse response) throws IOException {

    }
}
