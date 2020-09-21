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
 * 图书馆房间类型
 * @Author: Charles
 * @Date: 2020/9/20 21:48
 */

@Entity
@Getter
@Setter
@Where(clause = BaseCons.SOFT_DELETE)
@Table(name = "lib_category")
public class Category extends BaseEntity {

    public static final String ENTITY_NAME = "类型";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    @Column(name = "id")
    private Long id;

    /**
     * 类型名称
     */
    @NotBlank(message = "类型名称不能为空")
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Room> rooms;
}
