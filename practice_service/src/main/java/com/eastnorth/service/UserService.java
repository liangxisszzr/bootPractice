package com.eastnorth.service;

import com.eastnorth.pojo.Users;
import com.eastnorth.pojo.bo.UserBO;

/**
 * @author zuojianhou
 * @date   2020/4/8
 * @Description:
 */
public interface UserService {

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return true/false
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     * @param userBO
     * @return
     */
    Users createUser(UserBO userBO);

}