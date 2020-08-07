package com.baosight.shop.service;

import com.baosight.shop.domain.TemplateVo;

/**
 * @author wanghuaan
 * @date 2020/7/9
 **/
public interface IOnlinePaintService {

    /**
     * 根据模板code查询模板详情
     *
     * @param tmplCode 模板code
     * @return 模板展示实体
     */
    public TemplateVo selectTemplateByCode(String tmplCode);
}
