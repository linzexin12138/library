/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.domain.criteria;


import lombok.Data;
import java.sql.Timestamp;
import com.wteam.annotation.Query;

import java.time.LocalDate;
import java.util.List;
import io.swagger.annotations.ApiParam;
/**
* 用户预约信息 搜索类.
* @author charles
* @since 2020-10-02
*/
@Data
public class UserOrderQueryCriteria{

    @Query
    private Long userId;

    @Query
    private LocalDate date;

    @Query
    private Long orderTimeId;

    @Query
    private Long seatId;

    @Query
    private Integer status;

    @Query(type = Query.Type.IN, propName="seatId")
    private List<Long> seatIds;
}