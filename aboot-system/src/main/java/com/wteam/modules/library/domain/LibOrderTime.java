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


/**
 * @Author: Charles
 * @Date: 2020/9/22 17:45
 */

@Entity
@Getter
@Setter
@Where(clause = BaseCons.SOFT_DELETE)
@Table(name = "lib_order_time")
public class LibOrderTime extends BaseEntity {

    public static final String ENTITY_NAME = "预约时间段";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    @Column(name = "id")
    private Long id;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间", hidden = true)
    @NotNull(message = "开始时间不能为空")
    @Column(nullable = false, columnDefinition = "timestamp not null comment '开始时间'")
    private Timestamp startAt;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间", hidden = true)
    @NotNull(message = "结束时间不能为空")
    @Column(nullable = false, columnDefinition = "timestamp not null comment '结束时间'")
    private Timestamp endAt;
}
