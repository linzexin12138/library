package com.wteam.modules.library.service.impl;

import com.wteam.modules.library.domain.Room;
import com.wteam.modules.library.domain.criteria.RoomQueryCriteria;
import com.wteam.modules.library.domain.dto.RoomDTO;
import com.wteam.modules.library.domain.mapper.RoomMapper;
import com.wteam.modules.library.repository.RoomRepository;
import com.wteam.modules.library.service.RoomService;
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
@CacheConfig(cacheNames = "room")
@Transactional( readOnly = true, rollbackFor = Exception.class)
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    private final RedisUtils redisUtils;


    @Override
    @Cacheable(key = "'room:' + #p0")
    public RoomDTO findDTOById(Long id) {

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoomDTO create(Room resources) {
        Room room = roomRepository.save(resources);
        return roomMapper.toDto(room);
    }

    @Override
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
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            roomRepository.logicDelete(id);
        }
    }

    @Override
    public Map<String, Object> queryAll(RoomQueryCriteria criteria, Pageable pageable) {
        return null;
    }

    @Override
    public List<RoomDTO> queryAll(RoomQueryCriteria criteria) {
        return  roomMapper.toDto(roomRepository.findAll((root, cq, cb) -> QueryHelper.andPredicate(root, criteria, cb)));

    }

    @Override
    public void download(List<RoomDTO> queryAll, HttpServletResponse response) throws IOException {

    }
}
