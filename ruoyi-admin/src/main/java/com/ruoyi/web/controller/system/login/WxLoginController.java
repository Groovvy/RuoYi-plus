package com.ruoyi.web.controller.system.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.config.properties.WxProperties;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.LoginType;
import com.ruoyi.common.utils.http.HttpClientUtils;
import com.ruoyi.framework.shiro.token.UserToken;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author wanghuaan
 * @date 2020/8/13
 **/
@Controller
@RequestMapping("wx")
public class WxLoginController extends BaseController {
    Logger logger = LoggerFactory.getLogger(WxLoginController.class);

    @Autowired
    WxProperties wxProperties;
    @GetMapping("/wxLogin")
    public String wxLogin() throws UnsupportedEncodingException {
        //用户同意授权，获取code
        String url = "https://open.weixin.qq.com/connect/qrconnect?appid=" + wxProperties.getAppId() +
                "&redirect_uri=" + URLEncoder.encode(wxProperties.getRedirectUrl(),"utf-8")+
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=STATE#wechat_redirect";
        return "redirect:" + url;
    }

    @GetMapping("/wxCallback")
    public String wxCallback(String code, ModelMap map) throws UnsupportedEncodingException {
        try {
            // 通过code换取网页授权access_token
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxProperties.getAppId() +
                    "&secret=" + wxProperties.getAppSecret() +
                    "&code=" + code +
                    "&grant_type=authorization_code";
            JSONObject jsonObject = JSON.parseObject(HttpClientUtils.get(url));

            String openid = jsonObject.getString("openid");
            String access_Token = jsonObject.getString("access_token");

            logger.info(jsonObject.toJSONString());

            // 拉取用户信息
            url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_Token +
                    "&openid=" + openid +
                    "&lang=zh_CN";
            JSONObject userInfoJson = JSON.parseObject(HttpClientUtils.get(url));
            logger.info("UserInfo:" + userInfoJson);

            UserToken userToken = new UserToken(LoginType.WX_LOGIN, userInfoJson.getString("nickname"), openid, openid,userInfoJson);
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
}
