package com.ruoyi.framework.config;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.redis.RedisCacheManager;
import com.ruoyi.framework.util.RedisUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class CacheManagerConfig
{
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 缓存管理器 使用Ehcache实现
     */
    @Bean
    public EhCacheManager getEhCacheManager()
    {

        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getCacheManager("ruoyi");
        EhCacheManager em = new EhCacheManager();
        if (StringUtils.isNull(cacheManager))
        {
            em.setCacheManager(new net.sf.ehcache.CacheManager(
                    getCacheManagerConfigFileInputStream("classpath:ehcache/ehcache-shiro.xml")));

            return em;
        }
        else
        {
            em.setCacheManager(cacheManager);
            return em;
        }
    }

    /**
     * 返回配置文件流 避免ehcache配置文件一直被占用，无法完全销毁项目重新部署
     */
    protected InputStream getCacheManagerConfigFileInputStream(String configFile)
    {

        InputStream inputStream = null;
        try
        {
            inputStream = ResourceUtils.getInputStreamForPath(configFile);
            byte[] b = IOUtils.toByteArray(inputStream);
            InputStream in = new ByteArrayInputStream(b);
            return in;
        }
        catch (IOException e)
        {
            throw new ConfigurationException(
                    "Unable to obtain input stream for cacheManagerConfigFile [" + configFile + "]", e);
        }
        finally
        {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * shiro缓存管理器; 需要添加到securityManager中
     * 
     * @return
     */
    @Bean
    public RedisCacheManager getRedisCacheManager()
    {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisUtils(redisUtils);
        // redis中针对不同用户缓存
        redisCacheManager.setPrincipalIdFieldName("loginName");
        // 用户权限信息缓存时间
        redisCacheManager.setExpire(30 * 60 * 1000);

        return redisCacheManager;
    }
}
