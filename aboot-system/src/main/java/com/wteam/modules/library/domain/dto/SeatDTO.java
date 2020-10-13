package com.wteam.modules.library.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Charles
 * @Date: 2020/9/21 13:06
 */

@Data
public class SeatDTO implements Serializable {

    private Long id;

    private String name;

    private RoomDTO room;

}
