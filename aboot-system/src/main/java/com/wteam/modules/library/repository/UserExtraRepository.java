/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.repository;

import com.wteam.modules.library.domain.UserExtra;
import com.wteam.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
* 图书馆用户 存储层.
* @author mission
* @since 2020-09-23
*/
public interface UserExtraRepository extends BaseRepository<UserExtra, Long> {

//    @Modifying
//    @Query(value = "UPDATE user_extra SET order_status = order_status - 1 " +
//            "WHERE order_status != 0",nativeQuery = true)
//    void updateStatus();

    Optional<UserExtra> findByUserId(Long id);

    @Modifying
    @Query(value = "UPDATE user_extra SET sign_in_flag = 0", nativeQuery = true)
    void resetSignInFlag();
}