package com.wteam.modules.library.service.impl;

import com.wteam.modules.library.domain.Seat;
import com.wteam.modules.library.domain.criteria.SeatQueryCriteria;
import com.wteam.modules.library.domain.dto.SeatDTO;
import com.wteam.modules.library.domain.mapper.SeatMapper;
import com.wteam.modules.library.repository.SeatRepository;
import com.wteam.modules.library.service.SeatService;
import com.wteam.utils.PageUtil;
import com.wteam.utils.QueryHelper;
import com.wteam.utils.RedisUtils;
import com.wteam.utils.ValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Charles
 * @since 2020/9/21 11:02
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
    @Cacheable(key = "'id:' + #p0")
    public SeatDTO findDTOById(Long id) {
        Seat seat = seatRepository.findById(id).orElse(null);
        ValidUtil.notNull(seat,Seat.ENTITY_NAME,"id",id);
        return seatMapper.toDto(seat);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SeatDTO create(Seat resources) {
        Seat seat = seatRepository.save(resources);
        return seatMapper.toDto(seat);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
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
    public void deleteAll(Set<Long> ids) {
        seatRepository.logicDeleteInBatchById(ids);
        redisUtils.delByKeys("seat::id:",ids);
    }

    @Override
    public Map<String, Object> queryAll(SeatQueryCriteria criteria, Pageable pageable) {
        Page<Seat> page = seatRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(seatMapper::toDto));
    }

    @Override
    public List<SeatDTO> queryAll(SeatQueryCriteria criteria) {
        return  seatMapper.toDto(seatRepository.findAll((root, cq, cb) -> QueryHelper.andPredicate(root, criteria, cb)));
    }

    @Override
    public void download(List<SeatDTO> queryAll, HttpServletResponse response) throws IOException {

    }

    @Override
    @Cacheable(key = "'getIdListByRoomId:' + #p0")
    public List<Seat> getIdListByRoomId(Long roomId) {
        List<Seat> seatIdList = seatRepository.findAllByRoomId(roomId);
        redisUtils.set("seat::getIdListByRoomId:"+roomId, seatIdList);
        return seatIdList;

    }
}
