package com.baosight.mgmt.service.impl;

import com.baosight.common.core.service.IPritingService;
import com.baosight.mgmt.domain.Can;
import com.baosight.mgmt.mapper.CanMapper;
import com.baosight.mgmt.service.ICanService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 罐型管理Service业务层处理
 *
 * @author yhn
 * @date 2020-07-02
 */
@Service
public class CanServiceImpl extends IPritingService implements ICanService {

    protected final Logger logger = LoggerFactory.getLogger(CanServiceImpl.class);

    @Resource
    private CanMapper canMapper;

    /**
     * 查询罐型管理
     *
     * @param id 罐型管理ID
     * @return 罐型管理
     */
    @Override
    public Can selectCanById(Long id) {
        return canMapper.selectCanById(id);
    }

    /**
     * 查询罐型管理列表
     *
     * @param can 罐型管理
     * @return 罐型管理
     */
    @Override
    public List<Can> selectCanList(Can can) {
        return canMapper.selectCanList(can);
    }

    /**
     * 新增罐型管理
     *
     * @param can 罐型管理
     * @return 结果
     */
    @Override
    public int insertCan(Can can) {
        try {
            can.setCreateTime(DateUtils.getNowDate());
            can.setUpdateTime(DateUtils.getNowDate());
            return canMapper.insertCan(can);
        } catch (Exception e) {
            logger.error("新增罐型失败", e);
            throw new BusinessException("新增罐型失败!", e);
        }
    }

    /**
     * 修改罐型管理
     *
     * @param can 罐型管理
     * @return 结果
     */
    @Override
    public int updateCan(Can can) {
        can.setUpdateTime(DateUtils.getNowDate());
        return canMapper.updateCan(can);
    }

    /**
     * 删除罐型管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCanByIds(String ids) {
        return canMapper.deleteCanByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除罐型管理信息
     *
     * @param id 罐型管理ID
     * @return 结果
     */
    @Override
    public int deleteCanById(Long id) {
        return canMapper.deleteCanById(id);
    }

}
