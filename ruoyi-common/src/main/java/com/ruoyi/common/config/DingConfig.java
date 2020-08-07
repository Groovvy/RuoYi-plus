package com.ruoyi.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanghuaan
 * @date 2020/5/13
 **/
@Configuration
public class DingConfig {

    @Value("${ding.loginAppId}")
    private String loginAppId;

    @Value("${ding.loginAppSecret}")
    private String loginAppSecret;

    @Value("${ding.corpid}")
    private String corpid;

    @Value("${ding.redirectUrl}")
    private String redirectUrl;

    @Value("${ding.appKey}")
    private String appKey;

    @Value("${ding.appSecret}")
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
