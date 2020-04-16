package com.eastnorth.service;

import com.eastnorth.pojo.UserAddress;
import com.eastnorth.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {

    /**
     * 查询某用户下的所有地址
     * @param userId 用户 id
     * @return 地址集合list
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 用户新增地址
     * @param addressBO 地址信息
     */
    void addNewUserAddress(AddressBO addressBO);

    /**
     * 用户修改地址
     * @param addressBO 地址信息
     */
    void updateUserAddress(AddressBO addressBO);

    /**
     * 用户删除地址
     * @param userId 用户id
     * @param addressId 地址id
     */
    void deleteUserAddress(String userId, String addressId);

    /**
     * 修改默认地址
     * @param userId 用户id
     * @param addressId 地址id
     */
    void updateUserAddressToBeDefault(String userId, String addressId);

    /**
     * 根据用户id和地址id，查询具体的用户地址对象信息
     * @param userId 用户 id
     * @param addressId 地址 id
     * @return 具体地址信息
     */
    UserAddress queryUserAddress(String userId, String addressId);
}
