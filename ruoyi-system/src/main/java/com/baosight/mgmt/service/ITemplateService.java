package com.baosight.mgmt.service;

import com.baosight.mgmt.domain.Template;

import java.util.HashMap;
import java.util.List;

/**
 * 模板管理Service接口
 *
 * @author yhn
 * @date 2020-06-30
 */
public interface ITemplateService {
    /**
     * 查询模板管理
     *
     * @param id 模板管理ID
     * @return 模板管理
     */
    public Template selectTemplateById(Long id);

    /**
     * 查询模板管理列表
     *
     * @param template 模板管理
     * @return 模板管理集合
     */
    public List<Template> selectTemplateList(Template template);

    /**
     * 新增模板管理
     *
     * @param template 模板管理
     * @return 结果
     */
    public int insertTemplate(HashMap hashMap);

    /**
     * 修改模板管理
     *
     * @param template 模板管理
     * @return 结果
     */
    public int updateTemplate(Template template);

    /**
     * 批量删除模板管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTemplateByIds(String ids);

    /**
     * 删除模板管理信息
     *
     * @param id 模板管理ID
     * @return 结果
     */
    public int deleteTemplateById(Long id);
}
