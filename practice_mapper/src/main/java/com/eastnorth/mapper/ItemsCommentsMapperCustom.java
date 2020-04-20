package com.eastnorth.mapper;

import com.eastnorth.my.mapper.MyMapper;
import com.eastnorth.pojo.ItemsComments;
import com.eastnorth.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    void saveComments(Map<String, Object> map);

    List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);

}