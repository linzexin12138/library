/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 *  Notice: Whale Cloud Inc copyrights this specification.
 *  No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 */
package com.wteam.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.wteam.base.BaseCons;
import com.wteam.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
* 存储 持久类.
* @author mission
* @since 2019-11-03
*/
@Entity
@Getter
@Setter
@Where(clause = BaseCons.SOFT_DELETE)
@Table(name="tool_local_storage")
@NoArgsConstructor
public class LocalStorage extends BaseEntity{

    private static final long serialVersionUID = -4474670625813934455L;

    public final static String ENTITY_NAME ="存储";

    @Id
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*文件名*/
    @Column(name = "name")
    private String name;
    /*上传人*/
    @Column(name = "operate")
    private String operate;
    /*磁盘路径*/
    @Column(name = "path")
    private String path;
    /*文件真实名*/
    @Column(name = "real_name")
    private String realName;
    /*文件大小*/
    @Column(name = "size")
    private String size;
    /*文件后缀*/
    @Column(name = "suffix")
    private String suffix;
    /*文件类型*/
    @Column(name = "type")
    private String type;
    /*文件相对路径*/
    @Column(name = "url")
    private String url;

    public LocalStorage(String realName,String name, String suffix, String path, String type, String size, String operate,String url) {
        this.realName = realName;
        this.name = name;
        this.suffix = suffix;
        this.path = path;
        this.type = type;
        this.size = size;
        this.operate = operate;
        this.url=url;
    }

    public void copy(LocalStorage source){
            BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
        }
}