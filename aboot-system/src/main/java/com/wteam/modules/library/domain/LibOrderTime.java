package com.wteam.modules.library.domain;

import com.wteam.base.BaseCons;
import com.wteam.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalTime;


/**
 * @Author: Charles
 * @Date: 2020/9/22 17:45
 */

@Entity
@Getter
@Setter
@Where(clause = BaseCons.SOFT_DELETE)
@Table(name = "order_time")
public class LibOrderTime extends BaseEntity {

    public static final String ENTITY_NAME = "预约时间段";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    @Column(name = "id")
    private Long id;

    /**
     * 预约时间段
     */
    @ApiModelProperty(value = "预约时间段", hidden = true)
    @NotNull(message = "预约时间名称不能为空")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @NotNull(message = "预约开始时间不能为空")
    private LocalTime starTime;

    @Column(nullable = false)
    @NotNull(message = "预约结束时间不能为空")
    private LocalTime endTime;



}
