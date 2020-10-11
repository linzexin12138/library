package com.wteam.modules.library.repository;

import com.wteam.base.BaseRepository;
import com.wteam.modules.library.domain.Seat;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Charles
 * @Date: 2020/9/21 14:56
 */
public interface SeatRepository extends BaseRepository<Seat, Long> {

    @Query(value = "SELECT * FROM lib_seat WHERE room_id = ?1", nativeQuery = true)
    List<Seat> findAllByRoomId(Long roomId);
}
