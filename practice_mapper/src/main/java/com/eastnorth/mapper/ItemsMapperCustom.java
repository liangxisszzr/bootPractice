package com.eastnorth.mapper;

import com.eastnorth.pojo.vo.ItemCommentVO;
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

}