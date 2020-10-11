package com.wteam.modules.library.service;

import com.wteam.modules.library.domain.Category;
import com.wteam.modules.library.domain.Floor;
import com.wteam.modules.library.domain.criteria.CategoryQueryCriteria;
import com.wteam.modules.library.domain.criteria.FloorQueryCriteria;
import com.wteam.modules.library.domain.dto.CategoryDTO;
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
public interface CategoryService {
    /**
     * findById
     * @param id
     * @return
     */

    CategoryDTO findDTOById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    CategoryDTO create(Category resources);

    /**
     * update
     * @param resources
     */
    void update(Category resources);

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
    Map<String,Object> queryAll(CategoryQueryCriteria criteria, Pageable pageable);

    /**
     * queryAll
     * @param criteria /
     * @return
     */
    List<CategoryDTO> queryAll(CategoryQueryCriteria criteria);

    /**
     * 导出数据
     * @param queryAll
     * @param response
     */
    void download(List<CategoryDTO> queryAll, HttpServletResponse response) throws IOException;

}
