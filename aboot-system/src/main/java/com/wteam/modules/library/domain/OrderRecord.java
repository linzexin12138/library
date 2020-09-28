package com.wteam.modules.library.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.wteam.base.BaseCons;
import com.wteam.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @Author: Charles
 * @Date: 2020/9/23 14:00
 */

@Entity
@Getter
@Setter
@Where(clause = BaseCons.SOFT_DELETE)
@Table(name = "order_record", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"date", "order_time_id",
                "seat_id"})})
public class OrderRecord extends BaseEntity {

    public final static String ENTITY_NAME ="预约记录";

    public OrderRecord() {
    }

    public OrderRecord(Timestamp date, Long orderTimeId, Long seatId, Long userId) {
        this.date = date;
        this.orderTimeId = orderTimeId;
        this.seatId = seatId;
        this.userId = userId;
    }

    @Id
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 预约日期 */
    @ApiModelProperty( "预约日期")
    @NotNull
    @Column(name = "date")
    private Timestamp date;

    /** 预约时间段id */
    @ApiModelProperty( "预约时间段id")
    @NotNull
    @Column(name = "order_time_id")
    private Long orderTimeId;

    /** 座位id */
    @ApiModelProperty( "座位id")
    @NotNull
    @Column(name = "seat_id")
    private Long seatId;

    /** 用户id */
    @ApiModelProperty( "用户id")
    @NotNull(groups = Update.class)
    @Column(name = "user_id")
    private Long userId;


    @ApiModelProperty("预约记录的状态")
    @Column(name = "status", columnDefinition = "tinyint(1)  default 0 comment \'状态:0为未签到，1为已签到，3为已爽约，4为已取消\'")
    private Integer status;

    public void copy(OrderRecord source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
