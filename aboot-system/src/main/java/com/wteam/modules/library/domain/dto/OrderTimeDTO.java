package com.wteam.modules.library.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * @author Charles
 * @since 2020/9/28 19:07
 */

@Data
public class OrderTimeDTO implements Serializable {

    private Long id;

    private String name;

    private LocalTime starTime;

    private LocalTime endTime;

}
