package com.wteam.modules.library.domain.vo;

import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: Charles
 * @Date: 2020/9/26 20:01
 */

@Data
public class OrderRecordVO {

    @NotNull
    private Timestamp date;

    @NotEmpty
    private List<Long> seatIdList;

    @NotNull
    private Long userId;

    @NotEmpty
    private List<Long> orderTimeIdList;
}
