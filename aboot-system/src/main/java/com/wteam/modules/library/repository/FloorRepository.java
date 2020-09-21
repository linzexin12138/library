package com.wteam.modules.library.repository;

import com.wteam.base.BaseRepository;
import com.wteam.modules.library.domain.Floor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Charles
 * @Date: 2020/9/21 11:04
 */
public interface FloorRepository extends BaseRepository<Floor, Long> {

    @Modifying
    @Query(value = "UPDATE  lib_room SET floor_id = ?1 WHERE id IN ?2", nativeQuery = true)
    void addRoom(Long floorId, List<Long> roomIds);

    @Modifying
    @Query(value = "UPDATE lib_room SET floor_id = NULL WHERE floor_id = ?1", nativeQuery = true)
    void deleteRoom(Long floorId);
}
