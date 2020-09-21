package com.wteam.modules.library.service;

import com.wteam.modules.library.domain.Room;
import com.wteam.modules.library.domain.criteria.RoomQueryCriteria;
import com.wteam.modules.library.domain.dto.RoomDTO;
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
public interface RoomService {
    /**
     * findById
     * @param id
     * @return
     */

    RoomDTO findDTOById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    RoomDTO create(Room resources);

    /**
     * update
     * @param resources
     */
    void update(Room resources);

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
    Map<String,Object> queryAll(RoomQueryCriteria criteria, Pageable pageable);

    /**
     * queryAll
     * @param criteria /
     * @return
     */
    List<RoomDTO> queryAll(RoomQueryCriteria criteria);

    /**
     * 导出数据
     * @param queryAll
     * @param response
     */
    void download(List<RoomDTO> queryAll, HttpServletResponse response) throws IOException;

}
