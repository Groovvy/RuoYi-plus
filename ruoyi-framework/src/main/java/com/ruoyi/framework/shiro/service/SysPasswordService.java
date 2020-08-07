package com.ruoyi.framework.shiro.service;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.exception.user.UserPasswordRetryLimitExceedException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录密码方法
 *
 * @author ruoyi
 */
@Component
public class SysPasswordService
{
    @Autowired
    private RedisUtils redisManager;

    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;

    public static final String DEFAULT_RETRYLIMIT_KEY_PREFIX = "shiro:redisCache:retry:limit:";
    private String keyPrefix = DEFAULT_RETRYLIMIT_KEY_PREFIX;

    private String getRedisRetryLimitKey(String username)
    {
        return this.keyPrefix + username;
    }

    public void validate(SysUser user, String password)
    {
        String loginName = user.getLoginName();

        AtomicInteger retryCount = (AtomicInteger) redisManager.get(true, getRedisRetryLimitKey(loginName));

        if (retryCount == null)
        {
            retryCount = new AtomicInteger(0);

            redisManager.set(true, getRedisRetryLimitKey(loginName), retryCount);
        }

        if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue())
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.retry.limit.exceed", retryCount)));

            redisManager.set(true, getRedisRetryLimitKey(loginName), retryCount, 1 * 60 * 1000);

            throw new UserPasswordRetryLimitExceedException(Integer.valueOf(retryCount.incrementAndGet() - 1).intValue());

        }


        if (!matches(user, password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(SysUser user, String newPassword)
    {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

    public String encryptPassword(String username, String password, String salt)
    {
        return new Md5Hash(username + password + salt).toHex().toString();
    }

    public void clearLoginRecordCache(String username)
    {
        redisManager.del(true, getRedisRetryLimitKey(username));
    }
}
