package com.wteam.modules.library.domain.mapper;

import com.wteam.base.BaseMapper;
import com.wteam.modules.library.domain.Seat;
import com.wteam.modules.library.domain.dto.SeatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


/**
 * @Author: Charles
 * @Date: 2020/9/21 13:13
 */

@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeatMapper extends BaseMapper<SeatDTO, Seat> {

}
