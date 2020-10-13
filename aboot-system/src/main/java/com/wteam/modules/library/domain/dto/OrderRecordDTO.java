/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.domain.dto;

import com.wteam.modules.library.domain.OrderTime;
import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

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
    private LocalDate date;

    /** 预约时间段id */
    @ApiModelProperty( "预约时间段id")
    private OrderTimeDTO orderTime;

    /** 用户id */
    @ApiModelProperty( "用户id")
    private Long userId;

    /** 座位id */
    @ApiModelProperty( "座位id")
    private Long seatId;

    /** 座位状态*/
    @ApiModelProperty( "座位状态")
    private Integer status;

    /** 房间 */
    @ApiModelProperty( "房间名")
    private String roomName;

    /** 楼层 */
    @ApiModelProperty( "楼层名")
    private String floorName;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Timestamp createdAt;
}