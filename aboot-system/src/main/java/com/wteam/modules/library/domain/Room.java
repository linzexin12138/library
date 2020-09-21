package com.wteam.modules.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wteam.base.BaseCons;
import com.wteam.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 图书馆馆号类
 * @Author: Charles
 * @Date: 2020/9/20 21:46
 */

@Entity
@Getter
@Setter
@Where(clause = BaseCons.SOFT_DELETE)
@Table(name = "lib_room")
public class Room extends BaseEntity{

    public static final String ENTITY_NAME = "馆号";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = BaseEntity.Update.class)
    @Column(name = "id")
    private Long id;

    /**
     * 馆号
     */
    @NotBlank(message = "馆号不能为空")
    @Column(nullable = false)
    private String name;

    /**
     *楼层
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;

    /**
     * 类型
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

    @OneToMany(mappedBy = "room")
    private Set<Seat> seats;
}
