package com.baosight.common.service;


import com.baosight.common.domain.CommonFile;

import java.util.List;

/**
 * 附件公共Service接口
 *
 * @author yhn
 * @date 2020-07-07
 */
public interface ICommonFileService {
    /**
     * 查询附件公共
     *
     * @param id 附件公共ID
     * @return 附件公共
     */
    public CommonFile selectCommonFileById(Long id);

    /**
     * 查询附件公共列表
     *
     * @param commonFile 附件公共
     * @return 附件公共集合
     */
    public List<CommonFile> selectCommonFileList(CommonFile commonFile);

    /**
     * 新增附件公共
     *
     * @param commonFile 附件公共
     * @return 结果
     */
    public int insertCommonFile(CommonFile commonFile);

    /**
     * 修改附件公共
     *
     * @param commonFile 附件公共
     * @return 结果
     */
    public int updateCommonFile(CommonFile commonFile);

    /**
     * 批量删除附件公共
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCommonFileByIds(String ids);

    /**
     * 删除附件公共信息
     *
     * @param id 附件公共ID
     * @return 结果
     */
    public int deleteCommonFileById(Long id);
}
