package com.wteam.modules.library.service.impl;

import com.wteam.modules.library.domain.Category;
import com.wteam.modules.library.domain.Floor;
import com.wteam.modules.library.domain.criteria.CategoryQueryCriteria;
import com.wteam.modules.library.domain.criteria.FloorQueryCriteria;
import com.wteam.modules.library.domain.dto.CategoryDTO;
import com.wteam.modules.library.domain.dto.FloorDTO;
import com.wteam.modules.library.domain.mapper.CategoryMapper;
import com.wteam.modules.library.domain.mapper.FloorMapper;
import com.wteam.modules.library.repository.CategoryRepository;
import com.wteam.modules.library.repository.FloorRepository;
import com.wteam.modules.library.service.CategoryService;
import com.wteam.modules.library.service.FloorService;
import com.wteam.utils.PageUtil;
import com.wteam.utils.QueryHelper;
import com.wteam.utils.RedisUtils;
import com.wteam.utils.ValidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Charles
 * @Date: 2020/9/21 11:02
 */

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "category")
@Transactional( readOnly = true, rollbackFor = Exception.class)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final RedisUtils redisUtils;


    @Override
    @Cacheable(key = "'id:' + #p0")
    public CategoryDTO findDTOById(Long id) {
        Category seat = categoryRepository.findById(id).orElse(null);
        ValidUtil.notNull(seat,Category.ENTITY_NAME,"id",id);
        return categoryMapper.toDto(seat);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO create(Category resources) {
        Category category = categoryRepository.save(resources);
        return categoryMapper.toDto(category);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public void update(Category resources) {
        Category category = categoryRepository.findById(resources.getId()).orElse(null);
        ValidUtil.notNull(category,Category.ENTITY_NAME,"id",resources.getId());
        assert category != null;
        category.setName(resources.getName());
        categoryRepository.save(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Set<Long> ids) {
        redisUtils.delByKeys("category::id:",ids);
        categoryRepository.logicDeleteInBatchById(ids);
    }

    @Override
    public Map<String, Object> queryAll(CategoryQueryCriteria criteria, Pageable pageable) {
        Page<Category> page = categoryRepository.findAll((root, criteriaQuery, criteriaBuilder) ->  QueryHelper.andPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(categoryMapper::toDto));
    }

    @Override
    public List<CategoryDTO> queryAll(CategoryQueryCriteria criteria) {
        return  categoryMapper.toDto(categoryRepository.findAll((root, cq, cb) -> QueryHelper.andPredicate(root, criteria, cb)));

    }

    @Override
    public void download(List<CategoryDTO> queryAll, HttpServletResponse response) throws IOException {

    }
}
