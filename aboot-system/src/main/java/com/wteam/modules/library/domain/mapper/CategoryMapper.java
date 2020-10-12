package com.wteam.modules.library.domain.mapper;

import com.wteam.base.BaseMapper;
import com.wteam.modules.library.domain.Category;
import com.wteam.modules.library.domain.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Charles
 * @since 2020/9/21 13:19
 */

@Mapper(componentModel = "spring",uses = {RoomMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper extends BaseMapper<CategoryDTO, Category> {
}
