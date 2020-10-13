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
import java.time.LocalDate;

/**
 * @author Charles
 * @since 2020/9/23 14:00
 */

@Entity
@Getter
@Setter
@Where(clause = BaseCons.SOFT_DELETE)
@Table(name = "order_record")
public class OrderRecord extends BaseEntity {

    public final static String ENTITY_NAME ="预约记录";

    public OrderRecord() {
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
    private LocalDate date;

    /** 预约时间段 */
    @ApiModelProperty( "预约时间段")
    @ManyToOne
    @JoinColumn(name = "order_time_id")
    private OrderTime orderTime;

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

    //（提前离开的才需要签退）
    @ApiModelProperty("预约记录的状态")
    @Column(name = "status", columnDefinition = "tinyint(1) default 0 comment \'状态:0为未签到，1为已签到，2为已签退，3为已爽约，4为已取消\'")
    private Integer status;

    public void copy(OrderRecord source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
