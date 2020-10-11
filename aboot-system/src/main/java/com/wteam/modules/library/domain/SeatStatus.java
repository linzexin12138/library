package com.wteam.modules.library.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author charles
 * @since 2020/10/9 14:41
 */

@Data
public class SeatStatus implements Serializable {

    //座位id
    private Long id;
    //座位名
    private String name;

    //座位状态
    private List<OrderStatus> orderStatusList;

    @Data
    public class OrderStatus implements Serializable{
        //时间段id
        private Long id;
        //是否被预约
        private Boolean status;
    }

    public OrderStatus createOrderStatus(){
        return new OrderStatus();
    }
}
