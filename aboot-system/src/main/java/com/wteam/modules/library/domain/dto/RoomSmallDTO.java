package com.wteam.modules.library.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/21 13:03
 */

@Data
public class RoomSmallDTO implements Serializable {

    @NotNull
    private Long id;

    private List<Long> seatIds;
}
