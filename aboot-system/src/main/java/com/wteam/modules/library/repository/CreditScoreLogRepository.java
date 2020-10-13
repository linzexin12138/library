/*
 * Copyright © 2019-2020  Whale Cloud, Inc. All Rights Reserved.
 *
 * Notice: Whale Cloud Inc copyrights this specification.
 * No part of this specification may be reproduced in any form or means,
 *  without the prior written consent of Whale Cloud Inc.
 *
 */
package com.wteam.modules.library.repository;

import com.wteam.modules.library.domain.CreditScoreLog;
import com.wteam.base.BaseRepository;

/**
* 信用分记录 存储层.
* @author mission
* @since 2020-10-03
*/
public interface CreditScoreLogRepository extends BaseRepository<CreditScoreLog, Long> {
}