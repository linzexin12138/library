package com.wteam.modules.library.web;

import com.wteam.annotation.Log;
import com.wteam.annotation.permission.PermissionGroup;
import com.wteam.domain.vo.R;
import com.wteam.modules.library.domain.Card;
import com.wteam.modules.library.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 卡券 控制层
 * @author Charles
 * @since 2020/9/21 10:13
 */

@Api(value="卡券Controller",tags={"图书馆：卡券操作"})
@RequiredArgsConstructor
@RestController
@RequestMapping("api/card")
@PermissionGroup(value = "RULE", aliasPrefix = "卡券")
public class CardController {

    private final CardService cardService;

    @ApiOperation(value = "查询卡券")
    @Log("查询卡券")
    @GetMapping(value = "/get")
    @PreAuthorize("@R.check('USER:all','USER:list','RULE:all','RULE:list')")
    public R getCards(){
       return R.ok(cardService.findAll());
    }


    @ApiOperation(value = "新增卡券")
    @Log("新增卡券")
    @PostMapping("add")
    @PreAuthorize("@R.check('RULE:all','RULE:add')")
    public R create(@Validated @RequestBody Card resources){
        Assert.isNull(resources.getId(),"实体ID应为空");
        return R.ok(cardService.create(resources));
    }

    @ApiOperation(value = "修改卡券")
    @Log("修改卡券")
    @PostMapping("edit")
    @PreAuthorize("@R.check('RULE:all','RULE:edit')")
    public R edit(@Validated(Card.Update.class) @RequestBody Card resources){
        cardService.update(resources);
        return R.ok();
    }

    @ApiOperation(value = "删除卡券")
    @Log("删除卡券")
    @PostMapping("del")
    @PreAuthorize("@R.check('RULE:all','RULE:del')")
    public R delete(@RequestBody Set<Long> ids){
        cardService.delete(ids);
        return R.ok();
    }

}
