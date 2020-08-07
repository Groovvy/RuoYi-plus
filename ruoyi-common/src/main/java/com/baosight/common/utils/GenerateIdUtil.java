package com.baosight.common.utils;

import com.baosight.common.constant.GenerateConstans;
import com.ruoyi.common.exception.base.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 生成序列化ID工具类
 *
 * @author Yhn
 */
@Component
public class GenerateIdUtil {

    protected final Logger logger = LoggerFactory.getLogger(GenerateIdUtil.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 设置过期时间
     */
    private Date getTimeout() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 生成地址唯一id,格式暂时为DDXXXXX1
     */
    public String generateAdressId() {
        String prefix = GenerateConstans.ADDRESS_ID_PREFIX;
        StringBuffer sb = new StringBuffer();
        try {
            sb.append(prefix);
            long count = redisTemplate.opsForValue().increment(GenerateConstans.DIR + ":" + sb.toString(), 1);
            sb.append(String.format("%06d", count));
        } catch (Exception e) {
            logger.error("生成地址ID失败", e);
            throw new BaseException("生成地址ID失败");
        }
        return sb.toString();
    }

    /**
     * 生成订单唯一id,格式暂时为DD20200610XXXXX1
     */
    public String generateOrderId() {
        String prefix = GenerateConstans.ORDER_ID_PREFIX;
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            sb.append(prefix);
            sb.append(sdf.format(new Date()));
            long count = redisTemplate.opsForValue().increment(GenerateConstans.DIR + ":" + sb.toString(), 1);
            if (count == 1) {
                redisTemplate.expireAt(GenerateConstans.DIR + ":" + sb.toString(), getTimeout());
            }
            sb.append(String.format("%06d", count));
        } catch (Exception e) {
            logger.error("生成订单ID失败", e);
            throw new BaseException("生成订单ID失败");
        }
        return sb.toString();
    }

    /**
     * 生成罐型唯一id,格式暂时为GXXXXXX1
     */
    public String generateCanId() {
        String prefix = GenerateConstans.CAN_ID_PREFIX;
        StringBuffer sb = new StringBuffer();
        try {
            sb.append(prefix);
            long count = redisTemplate.opsForValue().increment(GenerateConstans.DIR + ":" + sb.toString(), 1);
            sb.append(String.format("%06d", count));
        } catch (Exception e) {
            logger.error("生成罐型ID失败", e);
            throw new BaseException("生成罐型ID失败");
        }
        return sb.toString();
    }

    /**
     * 生成内容物唯一id,格式暂时为NRWXXXX1
     */
    public String generateContId() {
        String prefix = GenerateConstans.CONT_ID_PREFIX;
        StringBuffer sb = new StringBuffer();
        try {
            sb.append(prefix);
            long count = redisTemplate.opsForValue().increment(GenerateConstans.DIR + ":" + sb.toString(), 1);
            sb.append(String.format("%05d", count));
        } catch (Exception e) {
            logger.error("生成内容物ID失败", e);
            throw new BaseException("生成内容物ID失败");
        }
        return sb.toString();
    }

    /**
     * 生成模板唯一id,格式暂时为MBXXXXX1
     */
    public String generateTmplId() {
        String prefix = GenerateConstans.TMPL_ID_PREFIX;
        StringBuffer sb = new StringBuffer();
        try {
            sb.append(prefix);
            long count = redisTemplate.opsForValue().increment(GenerateConstans.DIR + ":" + sb.toString(), 1);
            sb.append(String.format("%06d", count));
        } catch (Exception e) {
            logger.error("生成模板ID失败", e);
            throw new BaseException("生成模板ID失败");
        }
        return sb.toString();
    }

}
