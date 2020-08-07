package com.ruoyi.framework.shiro.service;

import com.ruoyi.common.constant.ShiroRedisConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.shiro.session.OnlineSession;
import com.ruoyi.framework.util.RedisUtils;
import com.ruoyi.system.domain.SysUserOnline;
import com.ruoyi.system.service.ISysUserOnlineService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 会话db操作处理
 * 
 * @author ruoyi
 */
@Component
public class SysShiroService
{
    @Autowired
    private ISysUserOnlineService onlineService;

    @Autowired
    private RedisUtils redisUtils;

    public String getRedisSessionKey(String SessionId)
    {
        return ShiroRedisConstants.keyPrefix + SessionId;

    }

    /**
     * 删除会话
     *
     * @param onlineSession 会话信息
     */
    public void deleteSession(OnlineSession onlineSession)
    {
        // 清除缓存 session
        String key = getRedisSessionKey(String.valueOf(onlineSession.getId()));
        redisUtils.del(true, key);
        onlineService.deleteOnlineById(String.valueOf(onlineSession.getId()));
    }

    /**
     * 获取会话信息
     *
     * @param sessionId
     * @return
     */
    public Session getSession(Serializable sessionId)
    {
        SysUserOnline userOnline = onlineService.selectOnlineById(String.valueOf(sessionId));
        return StringUtils.isNull(userOnline) ? null : createSession(userOnline);
    }

    public Session createSession(SysUserOnline userOnline)
    {
        OnlineSession onlineSession = new OnlineSession();
        if (StringUtils.isNotNull(userOnline))
        {
            onlineSession.setId(userOnline.getSessionId());
            onlineSession.setHost(userOnline.getIpaddr());
            onlineSession.setBrowser(userOnline.getBrowser());
            onlineSession.setOs(userOnline.getOs());
            onlineSession.setDeptName(userOnline.getDeptName());
            onlineSession.setLoginName(userOnline.getLoginName());
            onlineSession.setStartTimestamp(userOnline.getStartTimestamp());
            onlineSession.setLastAccessTime(userOnline.getLastAccessTime());
            onlineSession.setTimeout(userOnline.getExpireTime());
        }
        String key = getRedisSessionKey(String.valueOf(onlineSession.getId()));
        // 增加1000ms程序执行时间差
        redisUtils.set(true, key, onlineSession, onlineSession.getTimeout() + 1000);

        // 返回redis缓存session
        return StringUtils.isNotNull(redisUtils.get(true, key)) ? (OnlineSession) redisUtils.get(true, key) : onlineSession;
    }
}
