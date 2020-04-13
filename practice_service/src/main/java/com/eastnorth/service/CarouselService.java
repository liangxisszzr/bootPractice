package com.eastnorth.service;

import com.eastnorth.pojo.Carousel;

import java.util.List;

/**
 * @author zuojianhou
 * @date   2020/4/13
 * @Description:
 */
public interface CarouselService {

    /**
     * 查询所有轮播图列表
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);
}
