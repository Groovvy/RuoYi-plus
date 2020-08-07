package com.baosight.mgmt.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 模板管理对象 t_mgmt_template
 *
 * @author yhn
 * @date 2020-06-30
 */
public class Template extends BaseEntity {

    private static final long serialVersionUID = -4116920008232356504L;

    /**
     * id
     */
    private Long id;

    /**
     * 模板代码
     */
    @Excel(name = "模板代码")
    private String tmplCode;

    /**
     * 模板名称
     */
    @Excel(name = "模板名称")
    private String tmplName;

    /**
     * 模板描述
     */
    @Excel(name = "模板描述")
    private String tmplDesc;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    /**
     * 背景图片名
     */
    @Excel(name = "背景图片名")
    private String firstFileName;

    /**
     * 背景图片路径
     */
    @Excel(name = "背景图片路径")
    private String firstFilePath;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTmplCode(String tmplCode) {
        this.tmplCode = tmplCode;
    }

    public String getTmplCode() {
        return tmplCode;
    }

    public void setTmplName(String tmplName) {
        this.tmplName = tmplName;
    }

    public String getTmplName() {
        return tmplName;
    }

    public void setTmplDesc(String tmplDesc) {
        this.tmplDesc = tmplDesc;
    }

    public String getTmplDesc() {
        return tmplDesc;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getFirstFileName() {
        return firstFileName;
    }

    public void setFirstFileName(String firstFileName) {
        this.firstFileName = firstFileName;
    }

    public String getFirstFilePath() {
        return firstFilePath;
    }

    public void setFirstFilePath(String firstFilePath) {
        this.firstFilePath = firstFilePath;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("tmplCode", getTmplCode())
                .append("tmplName", getTmplName())
                .append("tmplDesc", getTmplDesc())
                .append("firstFileName", getFirstFileName())
                .append("firstFilePath", getFirstFilePath())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
