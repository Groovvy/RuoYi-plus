package com.baosight.mgmt.service;

import com.baosight.mgmt.domain.Contents;

import java.util.List;

/**
 * 内容物管理Service接口
 *
 * @author yhn
 * @date 2020-07-02
 */
public interface IContentsService {
    /**
     * 查询内容物管理
     *
     * @param id 内容物管理ID
     * @return 内容物管理
     */
    public Contents selectContentsById(Long id);

    /**
     * 查询内容物管理列表
     *
     * @param contents 内容物管理
     * @return 内容物管理集合
     */
    public List<Contents> selectContentsList(Contents contents);

    /**
     * 新增内容物管理
     *
     * @param contents 内容物管理
     * @return 结果
     */
    public int insertContents(Contents contents);

    /**
     * 修改内容物管理
     *
     * @param contents 内容物管理
     * @return 结果
     */
    public int updateContents(Contents contents);

    /**
     * 批量删除内容物管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteContentsByIds(String ids);

    /**
     * 删除内容物管理信息
     *
     * @param id 内容物管理ID
     * @return 结果
     */
    public int deleteContentsById(Long id);

}
