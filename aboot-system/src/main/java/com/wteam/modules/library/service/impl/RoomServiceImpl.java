package com.wteam.modules.library.service.impl;

import com.wteam.modules.library.domain.Room;
import com.wteam.modules.library.domain.criteria.RoomQueryCriteria;
import com.wteam.modules.library.domain.dto.RoomDTO;
import com.wteam.modules.library.domain.mapper.RoomMapper;
import com.wteam.modules.library.repository.RoomRepository;
import com.wteam.modules.library.service.RoomService;
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
 * @Author: Charles
 * @Date: 2020/9/21 11:02
 */

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "room")
@Transactional( readOnly = true, rollbackFor = Exception.class)
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    private final RedisUtils redisUtils;


    @Override
    @Cacheable(key = "'id:' + #p0")
    public RoomDTO findDTOById(Long id) {
        Room seat = roomRepository.findById(id).orElse(null);
        ValidUtil.notNull(seat,Room.ENTITY_NAME,"id",id);
        return roomMapper.toDto(seat);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoomDTO create(Room resources) {
        Room room = roomRepository.save(resources);
        return roomMapper.toDto(room);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void update(Room resources) {
        Room room = roomRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull(room,Room.ENTITY_NAME,"id",resources.getId());
        assert room != null;
        room.setName(resources.getName());
        roomRepository.save(room);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Set<Long> ids) {
        redisUtils.delByKeys("room::id:",ids);
        roomRepository.logicDeleteInBatchById(ids);
    }

    @Override
    public Map<String, Object> queryAll(RoomQueryCriteria criteria, Pageable pageable) {
        Page<Room> page = roomRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(roomMapper::toDto));
    }

    @Override
    public List<RoomDTO> queryAll(RoomQueryCriteria criteria) {
        return  roomMapper.toDto(roomRepository.findAll((root, cq, cb) -> QueryHelper.andPredicate(root, criteria, cb)));

    }

    @Override
    public void download(List<RoomDTO> queryAll, HttpServletResponse response) throws IOException {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editSeat(Long roomId, List<Long> seatIds) {

        roomRepository.deleteSeat(roomId);
        if (seatIds.size() > 0){
            roomRepository.addSeat(roomId,seatIds);
        }

    }
}
