package com.wteam.modules.library.domain;

import com.wteam.base.BaseCons;
import com.wteam.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @Author: Charles
 * @Date: 2020/9/25 15:59
 */

@Entity
@Getter
@Setter
@Where(clause = BaseCons.SOFT_DELETE)
@Table(name = "credit_score_log")
public class CreditScoreLog extends BaseEntity{

    public static final String ENTITY_NAME = "信用分记录";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = BaseEntity.Update.class)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "credit_score")
    private Integer creditScore;

}
