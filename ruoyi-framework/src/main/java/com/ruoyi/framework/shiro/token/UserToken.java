package com.ruoyi.framework.shiro.token;

import com.ruoyi.common.enums.LoginType;
import org.apache.shiro.authc.UsernamePasswordToken;

import java.util.Map;

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

    /**
     * qq信息
     */
    private Map<String,Object> map;


    public UserToken(LoginType loginType, final String username, final String password, final boolean rememberMe){
        super(username,password,rememberMe);
        this.loginType = loginType;
    }
    public UserToken(LoginType loginType, final String username, final String password, String code){
        super(username,password);
        this.loginType = loginType;
        this.code = code;
    }
    public UserToken(LoginType loginType, final String username, final String password, String code, Map<String,Object> map){
        super(username,password);
        this.loginType = loginType;
        this.map = map;
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

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
