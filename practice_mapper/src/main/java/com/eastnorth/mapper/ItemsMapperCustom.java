package com.eastnorth.mapper;

import com.eastnorth.pojo.vo.ItemCommentVO;
import com.eastnorth.pojo.vo.SearchItemsVO;
import com.eastnorth.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zuojianhou
 * @date   2020/4/14
 * @Description:
 */
public interface ItemsMapperCustom {

    /**
     * 查询商品评论信息
     * @param map 商品id及评论level
     * @return 评论信息集合list
     */
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    /**
     * 商品关键字搜索
     * @param map 查询条件
     * @return 模糊查询结果list
     */
    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    /**
     * 商品分类搜索
     * @param map 查询条件
     * @return 分类查询结果list
     */
    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    /**
     * 根据商品规格 id 查询商品信息（刷新购物车）
     * @param specIdsList 规格 id
     * @return 购物车商品list
     */
    List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);
}