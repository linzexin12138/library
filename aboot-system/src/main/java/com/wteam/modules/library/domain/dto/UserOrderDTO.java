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
import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;

/**
* 用户预约信息 DTO类.
* @author charles
* @since 2020-10-02
*/
@Data
public class UserOrderDTO implements Serializable {
    private Long id;
    private LocalDate date;
    private Long orderTimeId;
    private Long seatId;
    private Long userId;
    private Long orderId;
}