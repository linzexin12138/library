package com.wteam.modules.library.web;

import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.OrderRecord;
import com.wteam.modules.library.domain.UserExtra;
import com.wteam.modules.library.domain.criteria.UserExtraQueryCriteria;
import com.wteam.modules.library.service.UserExtraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author Charles
 * @since 2020/9/22 11:25
 */

@Api(value="图书馆用户扩展信息Controller",tags={"图书馆：用户扩展信息操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/userExtra")
public class UserExtraController {
    
    private final UserExtraService userExtraService;

    //@Log("查询用户扩展信息分页")
    @ApiOperation(value = "查询用户扩展信息")
    @GetMapping(value = "/page")
    @PreAuthorize("@R.check('USEREXTRA:all','USEREXTRA:list')")
    public R getOrderRecords(UserExtraQueryCriteria criteria, Pageable pageable){
        return R.ok(userExtraService.queryAll(criteria,pageable));
    }

    //@Log("查询用户扩展信息详情")
    @ApiOperation(value = "查询用户扩展信息详情")
    @GetMapping(value = "/get/{id}")
    @PreAuthorize("@R.check('USEREXTRA:all','USEREXTRA:list')")
    public R get(@PathVariable Long id){
        return R.ok(userExtraService.findDTOById(id));
    }

    //@Log("新增用户扩展信息")
    @ApiOperation(value = "新增用户扩展信息")
    @PostMapping(value = "/add")
    @PreAuthorize("@R.check('USEREXTRA:all','USEREXTRA:add')")
    public R create(@Validated @RequestBody UserExtra resources){
        return R.ok(userExtraService.create(resources));
    }

    //@Log("修改用户扩展信息")
    @ApiOperation(value = "修改用户扩展信息")
    @PostMapping(value = "/edit")
    @PreAuthorize("@R.check('USEREXTRA:all','USEREXTRA:edit')")
    public R update(@Validated(OrderRecord.Update.class) @RequestBody UserExtra resources){
        userExtraService.update(resources);
        return R.ok();
    }
    

}
