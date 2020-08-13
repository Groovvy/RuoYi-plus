package com.ruoyi.web.controller.system.login;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.ruoyi.common.config.properties.DdProperties;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.LoginType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.shiro.token.UserToken;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.taobao.api.ApiException;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @author wanghuaan
 * @date 2020/5/13
 **/
@Controller
@RequestMapping("/dd")
public class DdLoginController extends BaseController {

    @Autowired
    private DdProperties ddProperties;


    @GetMapping("/ddLogin")
    public void ddLogin(HttpServletResponse response){
        StringBuffer baseUrl=new StringBuffer("https://oapi.dingtalk.com/connect/qrconnect?");
        baseUrl.append("response_type=code&");
        baseUrl.append("scope=snsapi_login&");
        baseUrl.append("appid="+ ddProperties.getLoginAppId());
        baseUrl.append("&redirect_uri="+ ddProperties.getRedirectUrl());
        baseUrl.append("&state="+ UUID.randomUUID());
        try {
            response.sendRedirect(baseUrl.toString());
        } catch (IOException e) {
            logger.info("钉钉登录跳转异常 ",e);
        }
    }

    @GetMapping("/ddCallback")
    public String dingCallback(HttpServletRequest request) throws UnsupportedEncodingException {

        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
            OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
            req.setTmpAuthCode(request.getParameter("code"));
            OapiSnsGetuserinfoBycodeResponse response = client.execute(req, ddProperties.getLoginAppId(), ddProperties.getLoginAppSecret());

            UserToken userToken = new UserToken(LoginType.DD_LOGIN,response.getUserInfo().getNick(),response.getUserInfo().getOpenid(),response.getUserInfo().getOpenid());
            ShiroUtils.getSubject().login(userToken);
            SysUser currentUser = ShiroUtils.getSysUser();
            if(StringUtils.isEmpty(currentUser.getPhonenumber())){
                return  "redirect:/attachMobile";
            }
            return  "redirect:/index";

        }catch (AuthenticationException |ApiException e){
            return  "redirect:/login?msg="+ URLEncoder.encode(e.getMessage(),"utf-8");
        }catch (Exception e){
            return  "redirect:/login?msg="+ URLEncoder.encode("登录异常","utf-8");
        }


        /*// 根据unionid获取userid
        DingTalkClient client2 = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getUseridByUnionid");
        OapiUserGetUseridByUnionidRequest req2 = new OapiUserGetUseridByUnionidRequest();
        req2.setHttpMethod("GET");
        req2.setUnionid(response.getUserInfo().getUnionid());
        OapiUserGetUseridByUnionidResponse response2 = client2.execute(req2, getToken());
        logger.info("--------------------"+response2.getBody());

        //获取用户详情
        DingTalkClient client3 = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest req3 = new OapiUserGetRequest();
        req3.setUserid(response2.getUserid());
        req3.setHttpMethod("GET");
        OapiUserGetResponse response3 = client3.execute(req3, getToken());
        logger.info("--------------------"+response3);*/


    }

    public String getToken() throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(ddProperties.getAppKey());
        request.setAppsecret(ddProperties.getAppSecret());
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        logger.info("response:"+response);
        return response.getAccessToken();
    }

}
