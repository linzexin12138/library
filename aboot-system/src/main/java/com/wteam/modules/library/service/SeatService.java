package com.wteam.modules.library.service;

import com.wteam.modules.library.domain.Seat;
import com.wteam.modules.library.domain.criteria.SeatQueryCriteria;
import com.wteam.modules.library.domain.dto.SeatDTO;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/21 10:54
 */
public interface SeatService {
    /**
     * findById
     * @param id
     * @return
     */

    SeatDTO findDTOById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    SeatDTO create(Seat resources);

    /**
     * update
     * @param resources
     */
    void update(Seat resources);

    /**
     * delete
     * @param ids
     */
    void delete(Set<Long> ids);

    /**
     * queryAll
     * @param criteria
     * @param pageable
     * @return
     */
    Map<String,Object> queryAll(SeatQueryCriteria criteria, Pageable pageable);

    /**
     * queryAll
     * @param criteria /
     * @return
     */
    List<SeatDTO> queryAll(SeatQueryCriteria criteria);

    /**
     * 导出数据
     * @param queryAll
     * @param response
     */
    void download(List<SeatDTO> queryAll, HttpServletResponse response) throws IOException;

}
