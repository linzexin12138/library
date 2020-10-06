package com.wteam.modules.library.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * @Author: Charles
 * @Date: 2020/9/26 20:01
 */

@Data
public class OrderRecordVO {

    @NotNull
    private LocalDate date;

    @NotEmpty
    private List<Long> seatIdList;

    @ApiModelProperty(hidden = true)
    private Long userId;

    @NotEmpty
    private List<Long> orderTimeIdList;
}
