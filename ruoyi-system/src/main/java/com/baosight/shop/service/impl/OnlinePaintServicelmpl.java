package com.baosight.shop.service.impl;

import com.baosight.common.core.service.IPritingService;
import com.baosight.shop.domain.TemplateVo;
import com.baosight.shop.mapper.OnlinePaintMapper;
import com.baosight.shop.service.IOnlinePaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanghuaan
 * @date 2020/7/9
 **/
@Service
public class OnlinePaintServicelmpl extends IPritingService implements IOnlinePaintService {

    @Autowired
    private OnlinePaintMapper onlinePaintMapper;

    /**
     * 根据模板code查询模板详情
     *
     * @param tmplCode 模板code
     * @return 模板展示实体
     */
    @Override
    public TemplateVo selectTemplateByCode(String tmplCode) {
        return onlinePaintMapper.selectTemplateByCode(tmplCode);
    }
}
