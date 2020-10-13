/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.domain.mapper;

import com.wteam.base.BaseMapper;
import com.wteam.modules.library.domain.OrderTime;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.wteam.modules.library.domain.dto.OrderTimeDTO;


/**
* 预约时间 领域转换层.
* @author charles
* @since 2020-09-28
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderTimeMapper extends BaseMapper<OrderTimeDTO,OrderTime>{
}