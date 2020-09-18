/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package ${package}.domain.criteria;


import lombok.Data;
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
<#if queryColumns??>
import com.wteam.annotation.Query;
</#if>
<#if betweens??>
import java.util.List;
</#if>
import io.swagger.annotations.ApiParam;
/**
* ${tableComment} 搜索类.
* @author ${author}
* @since ${date}
*/
@Data
public class ${className}QueryCriteria{
<#if queryColumns??>
    <#list queryColumns as column>
    <#if column.queryType = '='>

    /** 精确 */
    @ApiParam( "${column.remark}精确查询")
    @Query
    private ${column.columnType} ${column.changeColumnName};
    </#if>
    <#if column.queryType = 'Like'>

    /** 模糊 */
    @ApiParam( "${column.remark}模糊查询")
    @Query(type = Query.Type.INNER_LIKE)
    private ${column.columnType} ${column.changeColumnName};
    </#if>
    <#if column.queryType = '!='>

    /** 不等于 */
    @ApiParam( "${column.remark}精确不等于查询")
    @Query(type = Query.Type.NOT_EQUAL)
    private ${column.columnType} ${column.changeColumnName};
    </#if>
    <#if column.queryType = 'NotNull'>

    /** 不为空 */
    @ApiParam( "${column.remark}不为空查询")
    @Query(type = Query.Type.NOT_NULL)
    private ${column.columnType} ${column.changeColumnName};
    </#if>
    <#if column.queryType = '>='>

    /** 大于等于 */
    @ApiParam( "${column.remark}大于等于查询")
    @Query(type = Query.Type.GREATER_THAN)
    private ${column.columnType} ${column.changeColumnName};
    </#if>
    <#if column.queryType = '<='>

    /** 小于等于 */
    @ApiParam( "${column.remark}小于等于查询")
    @Query(type = Query.Type.LESS_THAN)
    private ${column.columnType} ${column.changeColumnName};
    </#if>
    </#list>
</#if>
<#if betweens??>
    <#list betweens as column>

    /** 大于等于 */
    @ApiParam( "${column.remark}大于等于查询")
    @Query(propName = "${column.changeColumnName}",type = Query.Type.GREATER_THAN)
    private ${column.columnType} great${column.capitalColumnName};

    /** 小于等于 */
    @ApiParam( "${column.remark}小于等于查询")
    @Query(propName = "${column.changeColumnName}",type = Query.Type.LESS_THAN)
    private ${column.columnType} less${column.capitalColumnName};
    </#list>
</#if>
}