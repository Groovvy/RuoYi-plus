package com.ruoyi.framework.shiro.realm;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.enums.LoginType;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.user.UserBlockedException;
import com.ruoyi.common.exception.user.UserDeleteException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.shiro.token.UserToken;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author wanghuaan
 * @date 2020/8/12
 **/
public class QQRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(QQRealm.class);

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public String getName() {
        return LoginType.QQ_LOGIN.getType();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof UserToken) {
            return ((UserToken) token).getLoginType() == LoginType.QQ_LOGIN;
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

        log.info("---------------- qq登录 ----------------------");
        UserToken token = (UserToken) authenticationToken;
        String nickName = token.getUsername();
        String openid = token.getCode();
        Map<String,Object> userInfo = token.getMap();


        if(UserConstants.USER_OPENID_UNIQUE.equals(sysUserService.checkOpenIdUnique(getName(),openid))){
            SysUser defalutUser = new SysUser();
            defalutUser.setLoginName(nickName);
            defalutUser.setQqOpenId(openid);
            defalutUser.setAvatar(String.valueOf(userInfo.get("figureurl_qq")));
            defalutUser.setUserName(nickName);
            defalutUser.setSex(String.valueOf(userInfo.get("gender_type")));
            //角色
            Long[] roleIds = {Long.parseLong("2")};
            defalutUser.setRoleIds(roleIds);
            //岗位
            Long[] postIds = {Long.parseLong("4")};
            defalutUser.setPostIds(postIds);
            defalutUser.setStatus("0");
            //部门
            defalutUser.setDeptId(Long.parseLong("103"));

            sysUserService.insertUser(defalutUser);
        }

        SysUser user = sysUserService.selectUserByOpenId(getName(),openid);
        if(!user.getLoginName().equals(nickName) || !user.getAvatar().equals(String.valueOf(userInfo.get("figureurl_qq")))){
            user.setUserName(nickName);
            user.setAvatar(String.valueOf(userInfo.get("figureurl_qq")));
            sysUserService.updateUserInfo(user);
        }


        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(nickName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
            throw new LockedAccountException(new UserDeleteException().getMessage(), new UserDeleteException());
        }

        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(nickName, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked", user.getRemark())));
            throw new LockedAccountException(new UserBlockedException().getMessage(), new UserBlockedException());
        }


        AsyncManager.me().execute(AsyncFactory.recordLogininfor(nickName, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        recordLoginInfo(user);

        // 完成登录，此处已经不需要做密码校验
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, openid, getName());
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
