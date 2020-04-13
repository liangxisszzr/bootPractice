package com.eastnorth.mapper;

import com.eastnorth.pojo.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zuojianhou
 * @date   2020/4/13
 * @Description:
 */
public interface CategoryMapperCustom {

    /**
     * 查询子分类
     * @param rootCatId 父级 id
     * @return list
     */
    List<CategoryVO> getSubCatList(@Param("rootCatId") Integer rootCatId);

}
