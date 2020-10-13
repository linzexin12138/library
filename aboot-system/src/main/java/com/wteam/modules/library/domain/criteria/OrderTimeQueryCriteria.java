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
import java.util.List;
import io.swagger.annotations.ApiParam;
/**
* 预约时间 搜索类.
* @author charles
* @since 2020-09-28
*/
@Data
public class OrderTimeQueryCriteria{

    @Query
    private Long id;

    @Query
    private String name;


}