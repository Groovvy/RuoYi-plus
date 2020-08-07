package com.baosight.mgmt.mapper;

import com.baosight.mgmt.domain.Template;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模板管理Mapper接口
 *
 * @author yhn
 * @date 2020-06-30
 */
public interface TemplateMapper {
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
    public int insertTemplate(Template template);

    /**
     * 修改模板管理
     *
     * @param template 模板管理
     * @return 结果
     */
    public int updateTemplate(Template template);

    /**
     * 删除模板管理
     *
     * @param id 模板管理ID
     * @return 结果
     */
    public int deleteTemplateById(Long id);

    /**
     * 批量删除模板管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTemplateByIds(String[] ids);

    /**
     * 插入模板-罐型关联表
     *
     * @param tmplCode,canCode
     * @return 结果
     */
    public int insertTmplCan(@Param("tmplCode") String tmplCode, @Param("canCode") String canCode);

    /**
     * 插入模板-内容物关联表
     *
     * @param tmplCode,contCode
     * @return 结果
     */
    public int insertTmplCont(@Param("tmplCode") String tmplCode, @Param("contCode") String contCode);
}
