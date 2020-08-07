package com.ruoyi.common.enums;

/**
 * @author wanghuaan
 * @date 2020/5/13
 **/
public enum LoginType {

    //通用权限
    COMMON("common_realm"),
    //用户密码登录
    USER_PASSWORD("user_password_realm"),
    //手机验证码登录
    USER_PHONE("user_phone_realm"),
    //钉钉免密登录
    DING_LOGIN("ding_login_realm"),
    //微信免密登录
    WECHAT_LOGIN("wechat_login_realm");

    private String type;

    LoginType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
