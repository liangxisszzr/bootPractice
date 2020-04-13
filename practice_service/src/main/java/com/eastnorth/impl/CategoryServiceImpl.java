package com.eastnorth.impl;

import com.eastnorth.mapper.CategoryMapper;
import com.eastnorth.mapper.CategoryMapperCustom;
import com.eastnorth.pojo.Category;
import com.eastnorth.pojo.vo.CategoryVO;
import com.eastnorth.pojo.vo.NewItemsVO;
import com.eastnorth.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zuojianhou
 * @date   2020/4/13
 * @Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper mapper;
    @Autowired
    private CategoryMapperCustom mapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {

        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);

        List<Category> result = mapper.selectByExample(example);

        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {

        return mapperCustom.getSubCatList(rootCatId);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {

        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);

        return mapperCustom.getSixNewItemsLazy(map);
    }
}
