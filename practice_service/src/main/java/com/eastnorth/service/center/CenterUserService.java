package com.eastnorth.service.center;

import com.eastnorth.pojo.Users;
import com.eastnorth.pojo.bo.center.CenterUserBO;

public interface CenterUserService {

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     * @param userId 用户id
     * @param centerUserBO 用户提交信息
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 用户头像更新
     * @param userId 用户id
     * @param faceUrl 头像上传位置
     * @return 用户信息
     */
    Users updateUserFace(String userId, String faceUrl);
}
