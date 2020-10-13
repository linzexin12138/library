/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.service;

import com.wteam.modules.library.domain.UserExtra;
import com.wteam.modules.library.domain.dto.UserExtraDTO;
import com.wteam.modules.library.domain.criteria.UserExtraQueryCriteria;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
/**
* 图书馆用户 业务层.
* @author charles
* @since 2020-09-23
*/
public interface UserExtraService {

   /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(UserExtraQueryCriteria criteria, Pageable pageable);

   /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    List<UserExtraDTO> queryAll(UserExtraQueryCriteria criteria);


    /**
    * 根据ID查询
    * @param id ID
    * @return UserExtraDTO
    */
    UserExtraDTO findDTOById(Long id);

    /**
     * 根据用户ID查询
     * @param id
     * @return UserExtra
     */

    UserExtra findByUserId(Long id);

    /**
    * 创建
    * @param resources /
    * @return UserExtraDTO
    */
    UserExtraDTO create(UserExtra resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(UserExtra resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Set<Long> ids,Set<Long> userId);

   /**
   * 导出数据
   * @param queryAll 待导出的数据
   * @param response /
   * @throws IOException /
   */

   void download(List<UserExtraDTO> queryAll, HttpServletResponse response) throws IOException;

    void updateCreditScore(UserExtra userExtra);

    void resetSignInFlag();
}