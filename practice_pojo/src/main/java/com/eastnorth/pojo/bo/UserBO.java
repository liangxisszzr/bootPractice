package com.eastnorth.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: zuojianhou
 * @Date: 2020/4/9
 * @Description:
 */
@ApiModel(value = "用户对象", description = "从客户端,由用户传入的数据封装在此entity中")
public class UserBO {

    @ApiModelProperty(value = "用户名", name = "username", example = "zhangsan", required = true)
    private String username;
    @ApiModelProperty(value = "用户密码", name = "password", example = "123456a?", required = true)
    private String password;
    @ApiModelProperty(value = "确认密码", name = "confirmPassword", example = "123456a?")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserBO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
