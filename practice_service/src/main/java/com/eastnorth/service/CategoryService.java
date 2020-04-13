package com.eastnorth.service;

import com.eastnorth.pojo.Category;
import com.eastnorth.pojo.vo.CategoryVO;

import java.util.List;

/**
 * @author zuojianhou
 * @date   2020/4/13
 * @Description:
 */
public interface CategoryService {

    /**
     * 查询所有一级分类
     * @return list
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 查询子分类
     * @param rootCatId 父级 id
     * @return list
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);
}
