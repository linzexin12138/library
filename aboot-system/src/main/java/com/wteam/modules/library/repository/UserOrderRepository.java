/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.repository;

import com.wteam.modules.library.domain.UserOrder;
import com.wteam.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

/**
* 用户预约信息 存储层.
* @author mission
* @since 2020-10-02
*/
public interface UserOrderRepository extends BaseRepository<UserOrder, Long> {

    @Query(value = "SELECT COUNT(1) FROM user_order WHERE user_id = ?1 AND date = ?2 AND order_time_id IN ?3 AND deleted_at = NULL", nativeQuery = true)
    int checkRepeat(Long userId, LocalDate date, List<Long> orderTimeIdList);

    UserOrder findByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);

    void deleteAllByDateBefore(LocalDate localDate);
}