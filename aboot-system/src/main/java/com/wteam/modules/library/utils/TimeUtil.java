package com.wteam.modules.library.utils;

import java.time.LocalDate;
import java.time.Period;

/**
 * @Author: Charles
 * @Date: 2020/10/2 14:34
 */
public class TimeUtil {

    public static Boolean isToday(LocalDate date){
        Period period = Period.between(LocalDate.now(), date);
        if (period.getYears() == 0 && period.getMonths() == 0 && period.getDays() == 0){
            return true;
        }else {
            return false;
        }
    }

    public static Boolean isHistory(LocalDate date){
        return date.isBefore(LocalDate.now());
    }

    public static Boolean isTomorrow(LocalDate date){
        Period period = Period.between(LocalDate.now(), date);
        if (period.getYears() == 0 && period.getMonths() == 0 && period.getDays() == 1){
            return true;
        }else {
            return false;
        }
    }
}
