package com.wteam.modules.library.domain.mapper;

import com.wteam.base.BaseMapper;
import com.wteam.modules.library.domain.Floor;
import com.wteam.modules.library.domain.dto.FloorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Charles
 * @since 2020/9/21 11:07
 */
@Mapper(componentModel = "spring",uses = {RoomMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FloorMapper extends BaseMapper<FloorDTO, Floor> {
}
