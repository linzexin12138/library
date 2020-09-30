package com.wteam.modules.library.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Charles
 * @Date: 2020/9/29 20:01
 */

@Data
public class SeatStatus implements Serializable {

    private Long id;

    private Long seatId;

    //0代表今天，1代表明天，以此类推
    private Integer date;

    private Long orderTimeId;

    //0代表不可预约，1代表可预约，默认值为1
    private Boolean status;

}
