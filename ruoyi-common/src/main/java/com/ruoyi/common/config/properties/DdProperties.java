package com.ruoyi.common.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanghuaan
 * @date 2020/5/13
 **/
@Configuration
public class DdProperties {

    @Value("${dd.loginAppId}")
    private String loginAppId;

    @Value("${dd.loginAppSecret}")
    private String loginAppSecret;

    @Value("${dd.corpid}")
    private String corpid;

    @Value("${dd.redirectUrl}")
    private String redirectUrl;

    @Value("${dd.appKey}")
    private String appKey;

    @Value("${dd.appSecret}")
    private String appSecret;

    public String getAppSecret() {
        return appSecret;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getCorpid() {
        return corpid;
    }

    public String getLoginAppId() {
        return loginAppId;
    }

    public String getLoginAppSecret() {
        return loginAppSecret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
