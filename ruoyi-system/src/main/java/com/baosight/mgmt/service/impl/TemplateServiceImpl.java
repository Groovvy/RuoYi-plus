package com.baosight.mgmt.service.impl;

import com.baosight.mgmt.domain.Template;
import com.baosight.mgmt.mapper.TemplateMapper;
import com.baosight.mgmt.service.ITemplateService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 模板管理Service业务层处理
 *
 * @author yhn
 * @date 2020-06-30
 */
@Service
public class TemplateServiceImpl implements ITemplateService {
    @Resource
    private TemplateMapper templateMapper;

    /**
     * 查询模板管理
     *
     * @param id 模板管理ID
     * @return 模板管理
     */
    @Override
    public Template selectTemplateById(Long id) {
        return templateMapper.selectTemplateById(id);
    }

    /**
     * 查询模板管理列表
     *
     * @param template 模板管理
     * @return 模板管理
     */
    @Override
    public List<Template> selectTemplateList(Template template) {
        return templateMapper.selectTemplateList(template);
    }

    /**
     * 新增模板管理
     *
     * @param map 模板管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTemplate(HashMap map) {
        HashMap map1 = (HashMap) map.get("template");
        Template template = new Template();
        template.setTmplName(map1.get("tmplName").toString());
        template.setTmplDesc(map1.get("tmplDesc").toString());
        String tmplCode = map.get("tmplCode").toString();
        template.setTmplCode(tmplCode);
        template.setStatus(map1.get("status").toString());
        template.setCreateTime(DateUtils.getNowDate());
        template.setCreateBy(map.get("loginName").toString());
        template.setUpdateTime(DateUtils.getNowDate());
        template.setUpdateBy(map.get("loginName").toString());
        int rows = templateMapper.insertTemplate(template);

        //插入模板-罐型关联表
        List<String> can = (List<String>) map.get("can");
        for (String canCode : can) {
            templateMapper.insertTmplCan(tmplCode, canCode);
        }

        //插入模板-内容物关联表
        List<String> contents = (List<String>) map.get("contents");
        for (String contCode : contents) {
            templateMapper.insertTmplCont(tmplCode, contCode);
        }

        return rows;
    }

    /**
     * 修改模板管理
     *
     * @param template 模板管理
     * @return 结果
     */
    @Override
    public int updateTemplate(Template template) {
        template.setUpdateTime(DateUtils.getNowDate());
        return templateMapper.updateTemplate(template);
    }

    /**
     * 删除模板管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTemplateByIds(String ids) {
        return templateMapper.deleteTemplateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除模板管理信息
     *
     * @param id 模板管理ID
     * @return 结果
     */
    @Override
    public int deleteTemplateById(Long id) {
        return templateMapper.deleteTemplateById(id);
    }

}
