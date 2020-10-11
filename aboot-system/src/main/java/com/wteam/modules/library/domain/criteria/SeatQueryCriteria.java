package com.wteam.modules.library.domain.criteria;

import com.wteam.annotation.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Author: Charles
 * @Date: 2020/9/21 10:32
 */
@Data
public class SeatQueryCriteria {

    @ApiModelProperty("座位名, 按座位名模糊查询")
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @ApiModelProperty("部门编号, 数组类型, 无需传入")
    @Query(propName = "id", type = Query.Type.EQUAL, joinName = "room")
    private Long roomId;
}
