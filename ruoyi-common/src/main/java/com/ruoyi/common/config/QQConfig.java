package com.ruoyi.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanghuaan
 * @date 2020/8/12
 **/
@Configuration
public class QQConfig {

    @Value("${qq.appId}")
    private String appId;

    @Value("${qq.appKey}")
    private String appKey;

    @Value("${qq.redirectUrl}")
    private String redirectUrl;

    public String getAppId() {
        return appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
