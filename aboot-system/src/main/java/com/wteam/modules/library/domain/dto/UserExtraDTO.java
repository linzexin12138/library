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
* 图书馆用户 DTO类.
* @author charles
* @since 2020-09-23
*/
@Data
public class UserExtraDTO implements Serializable {
    private Long id;
    private Integer creditScore;
    private String studentId;
    private Long userId;
    private Integer orderStatus;
}