package com.wteam.modules.library.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: Charles
 * @Date: 2020/9/21 21:34
 */

@Data
public class FloorSmallDTO implements Serializable {

    @NotEmpty
    private Long id;

    private List<Long> roomIds;
}
