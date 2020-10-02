package com.wteam.modules.library.utils;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * @Author: Charles
 * @Date: 2020/10/2 14:34
 */
public class TimeUtil {

    public static Boolean isToday(Timestamp timestamp){
        return timestamp.toLocalDateTime().toLocalDate().equals(LocalDate.now());
    }
}
