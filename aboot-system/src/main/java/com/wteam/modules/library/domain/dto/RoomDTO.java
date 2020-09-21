package com.wteam.modules.library.domain.dto;

import com.wteam.modules.library.domain.Seat;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/21 13:03
 */

@Data
public class RoomDTO implements Serializable {

    private Long id;

    private String name;

    private Set<SeatDTO> seats;
}
