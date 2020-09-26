/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.repository;

import com.wteam.modules.library.domain.OrderRecord;
import com.wteam.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;

/**
* 预约记录 存储层.
* @author mission
* @since 2020-09-23
*/
public interface OrderRecordRepository extends BaseRepository<OrderRecord, Long> {

//    @Query(value = "SELECT COUNT(1) FROM order_record WHERE date = ?1 AND order_time_id = ?2 AND seat_id = ?3", nativeQuery = true)
//    Long findOrderRecord(Timestamp date, Long orderTimeId, Long seatId);


//    @Modifying
//    @Query(value = "UPDATE order_record SET user_id = ?1 WHERE user_id = NULLL AND date = ?2 AND order_time_id = ?3 AND seat_id = ?4", nativeQuery = true)
//    Integer order(Long userId, Timestamp date, Long orderTimeId, Long seatId);
}