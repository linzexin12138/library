package com.wteam.modules.library.repository;

import com.wteam.base.BaseRepository;
import com.wteam.modules.library.domain.Room;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Charles
 * @Date: 2020/9/21 14:55
 */
public interface RoomRepository extends BaseRepository<Room, Long>{

    @Modifying
    @Query(value = "UPDATE  lib_seat SET room_id = ?1 WHERE id IN ?2", nativeQuery = true)
    void addSeat(Long roomId, List<Long> seatIds);

    @Modifying
    @Query(value = "UPDATE lib_seat SET room_id = NULL WHERE room_id = ?1", nativeQuery = true)
    void deleteSeat(Long roomId);
}
