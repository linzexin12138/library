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
* 信用分记录 DTO类.
* @author charles
* @since 2020-10-03
*/
@Data
public class CreditScoreLogDTO implements Serializable {
    private Long id;

    /** 创建时间 */
    @ApiModelProperty( "创建时间")
    private Timestamp createdAt;

    /** 创建人 */
    @ApiModelProperty( "创建人")
    private Long createdBy;
    private Integer creditScore;
    private Long userId;
    private String reason;
}