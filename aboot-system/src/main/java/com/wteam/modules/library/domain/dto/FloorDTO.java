package com.wteam.modules.library.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/21 10:56
 */

@Data
public class FloorDTO implements Serializable {

    private Long id;

    private String name;

    private Set<RoomDTO> rooms;
}
