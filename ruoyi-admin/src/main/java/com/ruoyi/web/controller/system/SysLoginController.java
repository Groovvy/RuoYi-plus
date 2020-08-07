package com.ruoyi.web.controller.system;

import com.baosight.common.utils.RedisUtil;
import com.bsteel.uecp.sms.client.SendSMS;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.LoginType;
import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.framework.shiro.token.UserToken;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@Controller
public class SysLoginController extends BaseController
{


    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response)
    {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe)
    {
        UserToken token = new UserToken(LoginType.USER_PASSWORD,username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    @GetMapping("/unauth")
    public String unauth()
    {
        return "error/unauth";
    }


    @PostMapping("/sendSMS")
    @ResponseBody
    public AjaxResult sendSMS(String phone)
    {
        Random random = new Random();
        StringBuffer code = new StringBuffer();
        for (int i=0;i<6;i++) {
            code.append(random.nextInt(10));
        }
        redisUtil.set(phone,code.toString(),1800);
        //ShiroUtils.getSession().setAttribute(phone,code.toString());
        String msg = "验证码:"+code.toString()+" 有效期30分钟。";
        try {
            int resultSms;
            resultSms = SendSMS.sendSMS(phone, msg);
            logger.info("===================" + resultSms);
        } catch (Exception e) {
            logger.info("短信验证码发送失败",e);
            return error("短信验证码发送失败");
        }
        return success("验证码已发送");
    }

    @PostMapping("/phoneLogin")
    @ResponseBody
    public AjaxResult phoneLogin(String phone,String code)
    {
        try {
            UserToken token = new UserToken(LoginType.USER_PHONE,phone,phone,code);
            ShiroUtils.getSubject().login(token);

        }catch (BaseException e){
            String msg = "验证码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        } catch (Exception e) {
            logger.info("登录失败",e);
            return error("登录失败");
        }
        return success();
    }

    @GetMapping("/attachMobile")
    public String attachMobile(){
        return "attachMobile";
    }

    @PostMapping("/attach")
    @ResponseBody
    public AjaxResult attach(String phone,String code,String password){

        try {

            SysUser phoneUser = new SysUser();
            phoneUser.setPhonenumber(phone);
            if(UserConstants.USER_PHONE_NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(phoneUser))){
                return error("手机号已被绑定，请更换手机号");
            }

            String cacheCode = String.valueOf(redisUtil.get(phone));
            //String cacheCode = String.valueOf(ShiroUtils.getSession().getAttribute(phone));
            if(!cacheCode.equals(code)){
                return error("验证码不正确");
            }
            SysUser currentUser = ShiroUtils.getSysUser();
            currentUser.setPhonenumber(phone);
            currentUser.setSalt(ShiroUtils.randomSalt());
            currentUser.setPassword(passwordService.encryptPassword(currentUser.getLoginName(), password, currentUser.getSalt()));

            int row = sysUserService.updateUserInfo(currentUser);
            if(row > 0){
                ShiroUtils.setSysUser(currentUser);
                return success();
            }
            return error();

        }catch (Exception e){
            logger.info("绑定失败",e);
            return error("绑定失败");
        }

    }

}
