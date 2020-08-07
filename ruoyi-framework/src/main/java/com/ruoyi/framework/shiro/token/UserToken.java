package com.ruoyi.framework.shiro.token;

import com.ruoyi.common.enums.LoginType;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author wanghuaan
 * @date 2020/5/13
 **/
public class UserToken extends UsernamePasswordToken {

    /**
     * 登录方式
     */
    private LoginType loginType;
    /**
     * 手机验证码/第三方唯一标识
     */
    private String code;


    public UserToken(LoginType loginType, final String username, final String password, final boolean rememberMe){
        super(username,password,rememberMe);
        this.loginType = loginType;
    }
    public UserToken(LoginType loginType, final String username, final String password, String code){
        super(username,password);
        this.loginType = loginType;
        this.code = code;
    }
    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String openid) {
        this.code = code;
    }

}
