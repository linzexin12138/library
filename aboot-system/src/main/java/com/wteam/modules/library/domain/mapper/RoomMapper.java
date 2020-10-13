package com.wteam.modules.library.domain.mapper;

import com.wteam.base.BaseMapper;
import com.wteam.modules.library.domain.Room;
import com.wteam.modules.library.domain.dto.RoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Charles
 * @since 2020/9/21 13:09
 */

@Mapper(componentModel = "spring",uses = {FloorMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper extends BaseMapper<RoomDTO, Room> {
}
