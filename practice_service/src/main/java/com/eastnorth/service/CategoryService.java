package com.eastnorth.service;

import com.eastnorth.pojo.Category;
import com.eastnorth.pojo.vo.CategoryVO;
import com.eastnorth.pojo.vo.NewItemsVO;

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

    /**
     * 查询首页每个一级分类下的 6 条最新商品数据
     * @param rootCatId 父级 id
     * @return list
     */
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
