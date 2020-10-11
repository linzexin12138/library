package com.wteam.modules.library.service;

import com.wteam.modules.library.domain.Floor;
import com.wteam.modules.library.domain.criteria.FloorQueryCriteria;
import com.wteam.modules.library.domain.dto.FloorDTO;
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
public interface FloorService {
    /**
     * findById
     * @param id
     * @return
     */

    FloorDTO findDTOById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    FloorDTO create(Floor resources);

    /**
     * update
     * @param resources
     */
    void update(Floor resources);

    /**
     * delete
     * @param ids
     */
    void deleteAll(Set<Long> ids);

    /**
     * queryAll
     * @param criteria
     * @param pageable
     * @return
     */
    Map<String,Object> queryAll(FloorQueryCriteria criteria, Pageable pageable);

    /**
     * queryAll
     * @param criteria /
     * @return
     */
    List<FloorDTO> queryAll(FloorQueryCriteria criteria);

    /**
     * 导出数据
     * @param queryAll
     * @param response
     */
    void download(List<FloorDTO> queryAll, HttpServletResponse response) throws IOException;

    void editRoom(Long floorId, List<Long> roomIds);
}
