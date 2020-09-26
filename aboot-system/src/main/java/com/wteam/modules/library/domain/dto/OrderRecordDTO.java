/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.domain.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;

/**
* 预约记录 DTO类.
* @author charles
* @since 2020-09-23
*/
@Data
public class OrderRecordDTO implements Serializable {
    private Long id;


    /** 预约日期 */
    @ApiModelProperty( "预约日期")
    private Timestamp date;

    /** 预约时间段id */
    @ApiModelProperty( "预约时间段id")
    private Long orderTimeId;

    /** 用户id */
    @ApiModelProperty( "用户id")
    private Long userId;

    /** 座位id */
    @ApiModelProperty( "座位id")
    private Long seatId;

    /** 座位状态*/
    @ApiModelProperty( "座位状态")
    private Integer status;
}