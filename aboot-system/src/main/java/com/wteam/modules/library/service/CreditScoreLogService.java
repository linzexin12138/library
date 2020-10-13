/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.service;

import com.wteam.modules.library.domain.CreditScoreLog;
import com.wteam.modules.library.domain.dto.CreditScoreLogDTO;
import com.wteam.modules.library.domain.criteria.CreditScoreLogQueryCriteria;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
/**
* 信用分记录 业务层.
* @author charles
* @since 2020-10-03
*/
public interface CreditScoreLogService{

   /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(CreditScoreLogQueryCriteria criteria, Pageable pageable);

   /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    List<CreditScoreLogDTO> queryAll(CreditScoreLogQueryCriteria criteria);

    /**
    * 根据ID查询
    * @param id ID
    * @return CreditScoreLogDTO
    */
    CreditScoreLogDTO findDTOById(Long id);

    /**
    * 创建
    * @param resources /
    * @return CreditScoreLogDTO
    */
    CreditScoreLogDTO create(CreditScoreLog resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(CreditScoreLog resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Set<Long> ids);

   /**
   * 导出数据
   * @param queryAll 待导出的数据
   * @param response /
   * @throws IOException /
   */
   void download(List<CreditScoreLogDTO> queryAll, HttpServletResponse response) throws IOException;
}