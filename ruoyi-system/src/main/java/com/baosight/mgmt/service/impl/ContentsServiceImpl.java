package com.baosight.mgmt.service.impl;

import com.baosight.mgmt.domain.Contents;
import com.baosight.mgmt.mapper.ContentsMapper;
import com.baosight.mgmt.service.IContentsService;
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
 * 内容物管理Service业务层处理
 *
 * @author yhn
 * @date 2020-07-02
 */
@Service
public class ContentsServiceImpl implements IContentsService {

    protected final Logger logger = LoggerFactory.getLogger(ContentsServiceImpl.class);

    @Resource
    private ContentsMapper contentsMapper;

    /**
     * 查询内容物管理
     *
     * @param id 内容物管理ID
     * @return 内容物管理
     */
    @Override
    public Contents selectContentsById(Long id) {
        return contentsMapper.selectContentsById(id);
    }

    /**
     * 查询内容物管理列表
     *
     * @param contents 内容物管理
     * @return 内容物管理
     */
    @Override
    public List<Contents> selectContentsList(Contents contents) {
        return contentsMapper.selectContentsList(contents);
    }

    /**
     * 新增内容物管理
     *
     * @param contents 内容物管理
     * @return 结果
     */
    @Override
    public int insertContents(Contents contents) {
        try {
            contents.setCreateTime(DateUtils.getNowDate());
            contents.setUpdateTime(DateUtils.getNowDate());
            return contentsMapper.insertContents(contents);
        } catch (Exception e) {
            logger.error("新增内容物失败", e);
            throw new BusinessException("新增内容物失败!", e);
        }

    }

    /**
     * 修改内容物管理
     *
     * @param contents 内容物管理
     * @return 结果
     */
    @Override
    public int updateContents(Contents contents) {
        contents.setUpdateTime(DateUtils.getNowDate());
        return contentsMapper.updateContents(contents);
    }

    /**
     * 删除内容物管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteContentsByIds(String ids) {
        return contentsMapper.deleteContentsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除内容物管理信息
     *
     * @param id 内容物管理ID
     * @return 结果
     */
    @Override
    public int deleteContentsById(Long id) {
        return contentsMapper.deleteContentsById(id);
    }

}
