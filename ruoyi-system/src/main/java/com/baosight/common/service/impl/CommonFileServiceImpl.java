package com.baosight.common.service.impl;

import com.baosight.common.domain.CommonFile;
import com.baosight.common.mapper.CommonFileMapper;
import com.baosight.common.service.ICommonFileService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 附件公共Service业务层处理
 *
 * @author yhn
 * @date 2020-07-07
 */
@Service
public class CommonFileServiceImpl implements ICommonFileService {
    @Resource
    private CommonFileMapper commonFileMapper;

    /**
     * 查询附件公共
     *
     * @param id 附件公共ID
     * @return 附件公共
     */
    @Override
    public CommonFile selectCommonFileById(Long id) {
        return commonFileMapper.selectCommonFileById(id);
    }

    /**
     * 查询附件公共列表
     *
     * @param commonFile 附件公共
     * @return 附件公共
     */
    @Override
    public List<CommonFile> selectCommonFileList(CommonFile commonFile) {
        return commonFileMapper.selectCommonFileList(commonFile);
    }

    /**
     * 新增附件公共
     *
     * @param commonFile 附件公共
     * @return 结果
     */
    @Override
    public int insertCommonFile(CommonFile commonFile) {
        commonFile.setCreateTime(DateUtils.getNowDate());
        commonFile.setUpdateTime(DateUtils.getNowDate());
        return commonFileMapper.insertCommonFile(commonFile);
    }

    /**
     * 修改附件公共
     *
     * @param commonFile 附件公共
     * @return 结果
     */
    @Override
    public int updateCommonFile(CommonFile commonFile) {
        commonFile.setUpdateTime(DateUtils.getNowDate());
        return commonFileMapper.updateCommonFile(commonFile);
    }

    /**
     * 删除附件公共对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCommonFileByIds(String ids) {
        return commonFileMapper.deleteCommonFileByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除附件公共信息
     *
     * @param id 附件公共ID
     * @return 结果
     */
    @Override
    public int deleteCommonFileById(Long id) {
        return commonFileMapper.deleteCommonFileById(id);
    }
}
