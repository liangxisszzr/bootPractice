package com.eastnorth.controller;

import com.eastnorth.pojo.bo.ShopcartBO;
import com.eastnorth.utils.IMOOCJSONResult;
import com.eastnorth.utils.JsonUtils;
import com.eastnorth.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zuojianhou
 * @date   2020/4/15
 * @Description:
 */
@Api(value = "购物车接口controller", tags = {"购物车接口相关api"})
@RequestMapping("/shopcart")
@RestController
public class ShopCartController extends BaseController {

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(@RequestParam String userId, @RequestBody ShopcartBO shopcartBO, HttpServletRequest request, HttpServletResponse response) {

        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("");
        }

        System.out.println(shopcartBO);

        // 需要判断当前购物车中包含已经存在的商品,如果存在则累加购买数量
        String shopCartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopcartBO> shopCartList = null;
        if (StringUtils.isNotEmpty(shopCartJson)) {
            // redis中已经有购物车了
            shopCartList = JsonUtils.jsonToList(shopCartJson, ShopcartBO.class);
            // 判断购物车中是否存在已有商品,如果有的话 counts 累加
            boolean isHaving = false;
            for (ShopcartBO bo : shopCartList) {
                String tmpSpecId = bo.getSpecId();
                if (tmpSpecId.equals(shopcartBO.getSpecId())) {
                    bo.setBuyCounts(bo.getBuyCounts() + shopcartBO.getBuyCounts());
                    isHaving = true;
                    break;
                }
            }
            if (!isHaving) {
                shopCartList.add(shopcartBO);
            }
        } else {
            // redis中没有购物车
            shopCartList = new ArrayList<>();
            // 直接添加到购物车中
            shopCartList.add(shopcartBO);
        }

        // 覆盖现有 redis 中的购物车
        redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopCartList));

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "购物车删除商品", notes = "购物车删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public IMOOCJSONResult del(@RequestParam String userId, @RequestParam String itemSpecId, HttpServletRequest request, HttpServletResponse response) {
        
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return IMOOCJSONResult.errorMsg("参数不能为空");
        }

        String shopCartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        if (StringUtils.isNotEmpty(shopCartJson)) {
            // redis 中有购物车
            List<ShopcartBO> shopCartList = JsonUtils.jsonToList(shopCartJson, ShopcartBO.class);
            // 判断购物车中是否存在已有商品,如果有的话则删除
            for (ShopcartBO bo : shopCartList) {
                String tmpSpecId = bo.getSpecId();
                if (tmpSpecId.equals(itemSpecId)) {
                    shopCartList.remove(bo);
                    break;
                }
            }
            // 覆盖现有redis中的购物车
            redisOperator.set(FOODIE_SHOPCART + ":" +userId, JsonUtils.objectToJson(shopCartList));
        }
        
        return IMOOCJSONResult.ok();
    }
}
