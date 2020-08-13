package com.ruoyi.web.controller.system.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.config.properties.QqProperties;
import com.ruoyi.common.enums.LoginType;
import com.ruoyi.common.utils.http.HttpClientUtils;
import com.ruoyi.framework.shiro.token.UserToken;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @author wanghuaan
 * @date 2020/8/12
 **/
@Controller
@RequestMapping("/qq")
public class QqLoginController {
    private Logger logger = LoggerFactory.getLogger(QqLoginController.class);

    @Autowired
    private QqProperties qqProperties;

    @GetMapping("/qqLogin")
    public void qqLogin(HttpServletResponse response){
        StringBuffer baseUrl=new StringBuffer("https://graph.qq.com/oauth2.0/authorize?");
        baseUrl.append("response_type=code&");
        baseUrl.append("client_id="+ qqProperties.getAppId());
        baseUrl.append("&redirect_uri="+ qqProperties.getRedirectUrl());
        baseUrl.append("&state="+ UUID.randomUUID());
        try {
            response.sendRedirect(baseUrl.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/qqCallback")
    public String callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String token = getQQToken(request.getParameter("code"));
            logger.info("token:" + token);
            String openid = getOpenId(token);
            logger.info("openid:" + openid);
            JSONObject userInfo = getUserInfo(token, openid);

            UserToken userToken = new UserToken(LoginType.QQ_LOGIN, String.valueOf(userInfo.get("nickname")), openid, openid,userInfo);
            ShiroUtils.getSubject().login(userToken);
            SysUser currentUser = ShiroUtils.getSysUser();
            if (com.ruoyi.common.utils.StringUtils.isEmpty(currentUser.getPhonenumber())) {
                return "redirect:/attachMobile";
            }
            return "redirect:/index";
        }catch (AuthenticationException e){
            return  "redirect:/login?msg="+ URLEncoder.encode(e.getMessage(),"utf-8");
        }catch (Exception e){
            return  "redirect:/login?msg="+ URLEncoder.encode("登录异常","utf-8");
        }
    }

    public String getQQToken(String code) throws Exception {
        StringBuffer baseUrl= new StringBuffer("https://graph.qq.com/oauth2.0/token?");
        baseUrl.append("grant_type=authorization_code&");
        baseUrl.append("client_id="+ qqProperties.getAppId());
        baseUrl.append("&client_secret="+ qqProperties.getAppKey());
        baseUrl.append("&code="+code);
        baseUrl.append("&redirect_uri="+ qqProperties.getRedirectUrl());
        String token= HttpClientUtils.get(baseUrl.toString(), "UTF-8");
        token=token.split("&")[0];
        token=token.split("=")[1];
        System.out.println("tokenResponse:"+token);
        return token;
    }

    public String getOpenId(String token) throws Exception {
        String openidUrl="https://graph.qq.com/oauth2.0/me?access_token="+token;
        String openid= HttpClientUtils.get(openidUrl.toString(), "UTF-8");
        //openid:callback( {"client_id":"101826833","openid":"81B38A3BC7F27676F86FF5B32275978A"} );
        logger.info("openidResponse:"+openid);
        openid= StringUtils.substringBetween(openid, "\"openid\":\"", "\"}");
        return openid;
    }

    public JSONObject getUserInfo(String token, String openid) throws Exception {
        String infoUrl= "https://graph.qq.com/user/get_user_info?access_token="+token+"&oauth_consumer_key="+ qqProperties.getAppId()
                +"&openid="+openid;
        String user_info =HttpClientUtils.get(infoUrl.toString(), "UTF-8");
        logger.info("user_info:"+user_info);
        JSONObject parseObject = JSON.parseObject(user_info);
        return parseObject;
    }

}
