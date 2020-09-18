/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 文件路径工具
 * @author mission
 * @since 2019/07/07 19:47
 */
@Slf4j
@Configuration
public class PathUtil {

    //虚拟目录根路径
    private static String basePath;
    //
    private static String urlPrefix;

    //系统目录分割线
    private final static String seperator=System.getProperty("file.separator");
    //获得线程根路径
    private final static String threadBasePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //获得项目根路径
    private final static String projectRootPath=System.getProperty("user.dir");


    /**
     * 获得基本路径
     * @return
     */
    public static String basePath(){
        return basePath;
    }
    /**
     * 注入根路径
     * @param path
     */
    @Value("${file.path}")
    private void setPath(String path){
        //获得系统名字
        String os=System.getProperty("os.name");
        log.info("操作系统---"+os);
        log.info("线程根路径---"+threadBasePath);
        log.info("项目目录路径---"+projectRootPath);
        if (!path.endsWith("/")||!path.endsWith("\\")) {
            path=path+"/";
        }
        path=path.replace("/",seperator);
        log.info("资源存储路径---"+path);
        File tmpFile = new File(path);
        if (!tmpFile.exists()){
            tmpFile.mkdirs();
            log.info("初始创建----"+path);
        }
        this.basePath= path;
    }


    /**
     * url路径
     * @return
     */
    public static String fileUrlPrefix() {
        return urlPrefix;
    }

    @Value("${file.url-prefix}")
    public void setPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }


    public static String getSeperator() {
        return seperator;
    }

    public static String getThreadBasePath() {
        return threadBasePath;
    }

    public static String getProjectRootPath() {
        return projectRootPath;
    }


    /**
     * 获得文件相对路径
     */
    public static String toFilepath(String path){
        return path.replace("/",seperator);
    }
}
