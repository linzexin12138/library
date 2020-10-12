package com.wteam.modules.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wteam.base.BaseCons;
import com.wteam.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
public class Seat extends BaseEntity{

    public static final String ENTITY_NAME = "座位";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    @Column(name = "id")
    private Long id;

    /**
     * 名字
     */
    @NotBlank(message = "名字不能为空")
    @Column(nullable = false)
    private String name;

    /**
     * 状态:0为禁用，1可用
     */
    @Column(columnDefinition = "tinyint(1)  default 1 comment \'状态:0为禁用，1为可用\'")
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


}
