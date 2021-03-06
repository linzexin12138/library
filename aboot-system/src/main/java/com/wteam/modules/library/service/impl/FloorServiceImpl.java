package com.wteam.modules.library.service.impl;

import com.wteam.modules.library.domain.Floor;
import com.wteam.modules.library.domain.criteria.FloorQueryCriteria;
import com.wteam.modules.library.domain.dto.FloorDTO;
import com.wteam.modules.library.domain.mapper.FloorMapper;
import com.wteam.modules.library.repository.FloorRepository;
import com.wteam.modules.library.service.FloorService;
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
@CacheConfig(cacheNames = "floor")
@Transactional( readOnly = true, rollbackFor = Exception.class)
public class FloorServiceImpl implements FloorService {

    private final FloorRepository floorRepository;

    private final FloorMapper floorMapper;

    private final RedisUtils redisUtils;


    @Override
    @Cacheable(key = "'id:' + #p0")
    public FloorDTO findDTOById(Long id) {
        Floor seat = floorRepository.findById(id).orElse(null);
        ValidUtil.notNull(seat,Floor.ENTITY_NAME,"id",id);
        return floorMapper.toDto(seat);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FloorDTO create(Floor resources) {
        Floor floor = floorRepository.save(resources);
        return floorMapper.toDto(floor);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void update(Floor resources) {
        Floor floor = floorRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull(floor,Floor.ENTITY_NAME,"id",resources.getId());
        assert floor != null;
        floor.setName(resources.getName());
        floorRepository.save(floor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Set<Long> ids) {
        redisUtils.delByKeys("floor::id:",ids);
        floorRepository.logicDeleteInBatchById(ids);
    }

    @Override
    public Map<String, Object> queryAll(FloorQueryCriteria criteria, Pageable pageable) {
        Page<Floor> page = floorRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(floorMapper::toDto));
    }

    @Override
    public List<FloorDTO> queryAll(FloorQueryCriteria criteria) {
        return  floorMapper.toDto(floorRepository.findAll((root, cq, cb) -> QueryHelper.andPredicate(root, criteria, cb)));
    }

    @Override
    public void download(List<FloorDTO> queryAll, HttpServletResponse response) throws IOException {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRoom(Long floorId, List<Long> roomIds) {

        floorRepository.deleteRoom(floorId);
        if (roomIds.size() > 0){
            floorRepository.addRoom(floorId,roomIds);
        }

    }
}
