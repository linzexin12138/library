package com.wteam.modules.library.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.wteam.base.BaseCons;
import com.wteam.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @Author: Charles
 * @Date: 2020/10/2 17:32
 */

@Entity
@Getter
@Setter
@Where(clause = BaseCons.SOFT_DELETE)
@Table(name = "user_order", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"date", "order_time_id",
                "seat_id"})})
public class UserOrder extends BaseEntity {

    public final static String ENTITY_NAME ="用户预约信息";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = BaseEntity.Update.class)
    @Column(name = "id")
    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    @Column(name = "seat_id")
    private Long seatId;

    @NotNull
    @Column(name = "order_time_id")
    private Long orderTimeId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "order_id")
    private Long orderId;

    public void copy(UserOrder source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
