package com.wteam.modules.library.domain.dto;

import com.wteam.modules.library.domain.Room;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/21 13:04
 */

@Data
public class CategoryDTO implements Serializable {

    private Long id;

    private String name;

    private Set<RoomDTO> rooms;
}
