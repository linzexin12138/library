package com.wteam.modules.library.web;

import com.wteam.annotation.Log;
import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.Rule;
import com.wteam.modules.library.service.RuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

/**
 * 预约规则 控制层
 * @author Charles
 * @since 2020/9/21 10:13
 */

@Api(value="预约规则Controller",tags={"图书馆：预约规则操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/rule")
@PermissionGroup(value = "RULE", aliasPrefix = "预约规则")
public class RuleController {

    private final RuleService ruleService;

    @ApiOperation(value = "查询预约规则")
    @Log("查询预约规则")
    @GetMapping(value = "/get")
    @PreAuthorize("@R.check('USER:all','USER:list','RULE:all','RULE:list')")
    public R getRules(){
       return R.ok(ruleService.findAll());
    }


    @ApiOperation(value = "新增预约规则")
    @Log("新增预约规则")
    @PostMapping("add")
    @PreAuthorize("@R.check('RULE:all','RULE:add')")
    public R create(@Validated @RequestBody Rule resources){
        Assert.isNull(resources.getId(),"实体ID应为空");
        return R.ok(ruleService.create(resources));
    }

    @ApiOperation(value = "修改预约规则")
    @Log("修改预约规则")
    @PostMapping("edit")
    @PreAuthorize("@R.check('RULE:all','RULE:edit')")
    public R edit(@Validated(Rule.Update.class) @RequestBody Rule resources){
        ruleService.update(resources);
        return R.ok();
    }

    @ApiOperation(value = "删除预约规则")
    @Log("删除预约规则")
    @PostMapping("del")
    @PreAuthorize("@R.check('RULE:all','RULE:del')")
    public R delete(@RequestBody Set<Long> ids){
        ruleService.delete(ids);
        return R.ok();
    }

}
