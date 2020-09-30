package com.wteam.modules.library.domain.criteria;

import com.wteam.annotation.Query;
import lombok.Data;

/**
 * @Author: Charles
 * @Date: 2020/9/21 10:32
 */
@Data
public class RoomQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private Long id;

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

}
