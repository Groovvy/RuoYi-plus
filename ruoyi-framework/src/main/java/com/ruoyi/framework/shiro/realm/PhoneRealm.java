package com.ruoyi.framework.shiro.realm;

import com.ruoyi.common.utils.RedisUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.enums.LoginType;
import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.shiro.token.UserToken;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wanghuaan
 * @date 2020/5/15
 **/
public class PhoneRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String getName() {
        return LoginType.USER_PHONE.getType();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof UserToken) {
            return ((UserToken) token).getLoginType() == LoginType.USER_PHONE;
        } else {
            return false;
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        log.info("---------------- 短信登录 ----------------------");
        UserToken token = (UserToken) authenticationToken;
        String phone = token.getUsername();
        String code = token.getCode();

        String cacheCode = String.valueOf(redisUtil.get(phone));

        if(!code.equals(cacheCode)){
            throw new BaseException("验证码错误");
        }

        SysUser phoneUser = new SysUser();
        phoneUser.setPhonenumber(phone);
        if(UserConstants.USER_PHONE_UNIQUE.equals(sysUserService.checkPhoneUnique(phoneUser))){
            //注册
            SysUser defalutUser = new SysUser();
            defalutUser.setLoginName(phone);
            defalutUser.setUserName(phone);
            defalutUser.setPhonenumber(phone);
            //角色
            Long[] roleIds = {Long.parseLong("2")};
            defalutUser.setRoleIds(roleIds);
            //岗位
            Long[] postIds = {Long.parseLong("4")};
            defalutUser.setPostIds(postIds);
            defalutUser.setStatus("0");
            //部门
            defalutUser.setDeptId(Long.parseLong("103"));
            defalutUser.setSex("0");
            sysUserService.insertUser(defalutUser);
        }

        SysUser user = sysUserService.selectUserByPhoneNumber(phone);

        AsyncManager.me().execute(AsyncFactory.recordLogininfor(phone, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        recordLoginInfo(user);

        // 完成登录，此处已经不需要做密码校验
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, phone, getName());
        return authenticationInfo;
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user)
    {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        sysUserService.updateUserInfo(user);
    }
}
