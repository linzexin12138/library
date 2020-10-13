package com.wteam.modules.library.Listener;

import com.wteam.modules.library.domain.CreditScoreLog;
import com.wteam.modules.library.domain.OrderRecord;
import com.wteam.modules.library.domain.UserExtra;
import com.wteam.modules.library.service.CreditScoreLogService;
import com.wteam.modules.library.service.OrderRecordService;
import com.wteam.modules.library.service.UserExtraService;
import com.wteam.utils.RedisUtils;
import com.wteam.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @author charles
 * @since 2020/10/3 21:59
 */

@Component
public class OrderExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private OrderRecordService orderRecordService;

    @Autowired
    private CreditScoreLogService creditScoreLogService;

    @Autowired
    private UserExtraService userExtraService;

    private final int NO_COME = 3;

    public OrderExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        //message.toString()可以获取失效的key

        String expiredKey = message.toString();
        if(expiredKey.startsWith("orderId:")){
            Integer status = (Integer) redisUtils.get(expiredKey);
            Long orderId = Long.parseLong(expiredKey.substring(8));
            OrderRecord orderRecord = orderRecordService.findById(orderId);

            //用户没有签到
            if (orderRecord.getStatus() == 0){
                orderRecord.setStatus(NO_COME);

                CreditScoreLog creditScoreLog = new CreditScoreLog();
                creditScoreLog.setUserId(SecurityUtils.getId());
                creditScoreLog.setReason("爽约，扣除"+NO_COME +"分信用分");
                creditScoreLog.setCreditScore(-NO_COME);

                UserExtra userExtra = userExtraService.findByUserId(orderRecord.getUserId());
                int score = userExtra.getCreditScore() - NO_COME;
                userExtra.setCreditScore(score);

                creditScoreLogService.create(creditScoreLog);
                orderRecordService.updateStatus(orderRecord);
                userExtraService.updateCreditScore(userExtra);
            }
        }
    }
}
