package com.wteam.modules.library.web;

import com.wteam.annotation.Log;
import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.Category;
import com.wteam.modules.library.domain.criteria.CategoryQueryCriteria;
import com.wteam.modules.library.service.CategoryService;
import com.wteam.modules.library.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 类型 控制层
 * @Author: Charles
 * @Date: 2020/9/21 10:13
 */

@Api(value="类型Controller",tags={"图书馆：类型操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/category")
@PermissionGroup(value = "CATEGORY", aliasPrefix = "类型")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "查询类型")
    @Log("查询类型")
    @GetMapping(value = "/get")
    @PreAuthorize("@R.check('USER:all','USER:list','CATEGORY:all','CATEGORY:list')")
    public R getCategories(CategoryQueryCriteria criteria){
       return R.ok(categoryService.queryAll(criteria));
    }


    @ApiOperation(value = "新增类型")
    @Log("新增类型")
    @PostMapping("add")
    @PreAuthorize("@R.check('CATEGORY:all','CATEGORY:add')")
    public R create(@Validated @RequestBody Category resources){
        Assert.isNull(resources.getId(),"实体ID应为空");
        return R.ok(categoryService.create(resources));
    }

    @ApiOperation(value = "修改类型")
    @Log("修改类型")
    @PostMapping("edit")
    @PreAuthorize("@R.check('CATEGORY:all','CATEGORY:edit')")
    public R edit(@Validated(Category.Update.class) @RequestBody Category resources){
        categoryService.update(resources);
        return R.ok();
    }

    @ApiOperation(value = "删除类型")
    @Log("删除类型")
    @PostMapping("del")
    @PreAuthorize("@R.check('CATEGORY:all','CATEGORY:del')")
    public R delete(@RequestBody Set<Long> ids){
        categoryService.delete(ids);
        return R.ok();
    }

    /**
     * 导出数据
     * @param criteria /
     * @return response
     */
    @ApiOperation(value = "导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@R.check('CATEGORY:all','CATEGORY:list','USER:all','USER:list')")
    public void download(CategoryQueryCriteria criteria, HttpServletResponse response) throws IOException {
        categoryService.download(categoryService.queryAll(criteria),response);
    }
}
