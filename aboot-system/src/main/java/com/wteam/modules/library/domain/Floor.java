package com.wteam.modules.library.domain;

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
 * 图书馆楼层类
 * @Author: Charles
 * @Date: 2020/9/20 21:41
 */

@Entity
@Getter
@Setter
@Where(clause = BaseCons.SOFT_DELETE)
@Table(name = "lib_floor")
public class Floor extends BaseEntity {

    public static final String ENTITY_NAME = "楼层";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    @Column(name = "id")
    private Long id;

    /**
     * 楼层名
     */
    @NotBlank(message = "楼层名不能为空")
    @Column(nullable = false)
    private String name;

    /**
     * 房间
     */
    @OneToMany(mappedBy = "floor")
    private Set<Room> rooms;
}
