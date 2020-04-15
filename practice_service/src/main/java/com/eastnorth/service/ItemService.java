package com.eastnorth.service;

import com.eastnorth.pojo.Items;
import com.eastnorth.pojo.ItemsImg;
import com.eastnorth.pojo.ItemsParam;
import com.eastnorth.pojo.ItemsSpec;
import com.eastnorth.pojo.vo.CommentLevelCountsVO;
import com.eastnorth.pojo.vo.ItemCommentVO;
import com.eastnorth.pojo.vo.ShopcartVO;
import com.eastnorth.utils.PagedGridResult;

import java.util.List;

/**
 * @author zuojianhou
 * @date   2020/4/14
 * @Description:
 */
public interface ItemService {

    /**
     * 根据商品id查询详情
     * @param itemId 商品id
     * @return 商品详细
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     * @param itemId 商品id
     * @return 商品图片信息
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     * @param itemId 商品id
     * @return 商品规格信息
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     * @param itemId 商品id
     * @return 商品参数
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品的评价等级数量
     * @param itemId 商品id
     * @return 各级评价总数
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品评价（分页）
     * @param itemId 商品id
     * @param level 评论等级
     * @param page 页数
     * @param pageSize 每页显示数
     * @return 评论信息集合list
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 根据关键字搜索商品列表
     * @param keywords 搜索关键字
     * @param sort 排序方式
     * @param page 分页数
     * @param pageSize 分页每页条数
     * @return 模糊查询结果
     */
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 根据分类 id 搜索商品列表
     * @param catId 类 id
     * @param sort 排序方式
     * @param page 分页数
     * @param pageSize 分页每页条数
     * @return 分类查询结果
     */
    PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据商品规格 id 查询商品信息（刷新购物车）
     * @param specIds 规格 id
     * @return 购物车商品list
     */
    List<ShopcartVO> queryItemsBySpecIds(String specIds);

}
