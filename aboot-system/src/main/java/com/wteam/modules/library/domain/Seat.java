package com.wteam.modules.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wteam.base.BaseCons;
import com.wteam.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 座位类
 * @Author: Charles
 * @Date: 2020/9/20 22:27
 */

@Entity
@Getter
@Setter
@Where(clause = BaseCons.SOFT_DELETE)
@Table(name = "lib_seat")
public class Seat {

    public static final String ENTITY_NAME = "座位";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = BaseEntity.Update.class)
    @Column(name = "id")
    private Long id;

    /**
     * 状态:0为不可预约，1为可预约，2为已预约
     */
    @NotNull
    @Column(name = "status")
    private Integer status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
