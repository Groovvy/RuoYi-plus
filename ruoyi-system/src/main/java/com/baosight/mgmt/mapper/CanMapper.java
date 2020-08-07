package com.baosight.mgmt.mapper;

import com.baosight.mgmt.domain.Can;

import java.util.List;

/**
 * 罐型管理Mapper接口
 *
 * @author yhn
 * @date 2020-07-02
 */
public interface CanMapper {
    /**
     * 查询罐型管理
     *
     * @param id 罐型管理ID
     * @return 罐型管理
     */
    public Can selectCanById(Long id);

    /**
     * 查询罐型管理列表
     *
     * @param can 罐型管理
     * @return 罐型管理集合
     */
    public List<Can> selectCanList(Can can);

    /**
     * 新增罐型管理
     *
     * @param can 罐型管理
     * @return 结果
     */
    public int insertCan(Can can);

    /**
     * 修改罐型管理
     *
     * @param can 罐型管理
     * @return 结果
     */
    public int updateCan(Can can);

    /**
     * 删除罐型管理
     *
     * @param id 罐型管理ID
     * @return 结果
     */
    public int deleteCanById(Long id);

    /**
     * 批量删除罐型管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCanByIds(String[] ids);
}
