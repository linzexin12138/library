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
import com.wteam.modules.library.domain.UserOrder;
import com.wteam.modules.library.domain.dto.UserOrderDTO;
import com.wteam.modules.library.domain.criteria.UserOrderQueryCriteria;
import com.wteam.modules.library.domain.vo.OrderRecordVO;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
/**
* 用户预约信息 业务层.
* @author charles
* @since 2020-10-02
*/
public interface UserOrderService{

   /**
    * 查询数据分页
    * @param criteria
    * @param pageable
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(UserOrderQueryCriteria criteria, Pageable pageable);

   /**
    * 查询所有数据不分页
    * @param criteria
    * @return
    */
    List<UserOrderDTO> queryAll(UserOrderQueryCriteria criteria);

    /**
    * 根据ID查询
    * @param id ID
    * @return UserOrderDTO
    */
    UserOrderDTO findDTOById(Long id);



    /**
    * 创建
    * @param resources /
    * @return UserOrderDTO
    */
    UserOrderDTO create(UserOrder resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(UserOrder resources);

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
   void download(List<UserOrderDTO> queryAll, HttpServletResponse response) throws IOException;

    boolean checkRepeat(Long userId, LocalDate date, List<Long> orderTimeIdList);

    void create(List<OrderRecord> orderRecordList);

    void deleteByOrderId(Long orderId);

    void deleteHistory();
}