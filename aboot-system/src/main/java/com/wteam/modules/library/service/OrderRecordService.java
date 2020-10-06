/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.service;

import com.wteam.modules.library.domain.OrderRecord;
import com.wteam.modules.library.domain.dto.OrderRecordDTO;
import com.wteam.modules.library.domain.criteria.OrderRecordQueryCriteria;
import com.wteam.modules.library.domain.vo.OrderRecordVO;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
/**
* 预约记录 业务层.
* @author charles
* @since 2020-09-23
*/
public interface OrderRecordService{

   /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(OrderRecordQueryCriteria criteria, Pageable pageable);

   /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    List<OrderRecordDTO> queryAll(OrderRecordQueryCriteria criteria);

    /**
    * 根据ID查询
    * @param id ID
    * @return OrderRecordDTO
    */
    OrderRecordDTO findDTOById(Long id);

    /**
     * 根据ID查询
     * @param id ID
     * @return OrderRecord
     */
    OrderRecord findById(Long id);
    /**
    * 创建
    * @param resources /
    * @return OrderRecordDTO
    */
    OrderRecordDTO create(OrderRecord resources);

    List<OrderRecord> create(OrderRecordVO orderRecordVO);


    /**
    * 编辑
    * @param resources /
    */
    void update(OrderRecord resources);

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
   void download(List<OrderRecordDTO> queryAll, HttpServletResponse response) throws IOException;

    void updateStatus(OrderRecord orderRecord);

    OrderRecord findByIdAndUserId(Long orderRecordId, Long userId);

}