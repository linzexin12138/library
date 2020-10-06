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

/**
 * @Author: Charles
 * @Date: 2020/9/22 12:48
 */

@Entity
@Getter
@Setter
@Where(clause = BaseCons.SOFT_DELETE)
@Table(name = "user_extra")
public class UserExtra extends BaseEntity{

    public final static String ENTITY_NAME ="用户账号扩展信息";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = BaseEntity.Update.class)
    @Column(name = "id")
    private Long id;

    /**
     * 学号
     */
    @Column(name = "student_id")
    private String studentId;

    /**
     * 信用分
     */
    @Column(name = "credit_score",columnDefinition = "int default 100 comment \'信用分\'")
    private Integer creditScore;

    @Column(name = "user_id")
    @NotNull
    private Long userId;

    public void copy(UserExtra source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
