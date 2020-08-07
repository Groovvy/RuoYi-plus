package com.baosight.shop.domain;

import com.baosight.common.domain.CommonFile;
import com.baosight.mgmt.domain.Can;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * @author wanghuaan
 * @date 2020/7/9
 **/
public class TemplateVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 模板ID */
    private int id;

    /** 模板代码 */
    private String tmplCode;

    /** 模板名称 */
    private String tmplName;

    /** 模板描述 */
    private String tmplDesc;

    /** 状态 */
    private String status;

    /** 罐型 */
    private Can can;

    /** 资源文件 */
    private List<CommonFile> files;

    public TemplateVo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTmplCode() {
        return tmplCode;
    }

    public void setTmplCode(String tmplCode) {
        this.tmplCode = tmplCode;
    }

    public String getTmplName() {
        return tmplName;
    }

    public void setTmplName(String tmplName) {
        this.tmplName = tmplName;
    }

    public String getTmplDesc() {
        return tmplDesc;
    }

    public void setTmplDesc(String tmplDesc) {
        this.tmplDesc = tmplDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Can getCan() {
        return can;
    }

    public void setCan(Can can) {
        this.can = can;
    }

    public List<CommonFile> getFiles() {
        return files;
    }

    public void setFiles(List<CommonFile> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "TemplateVo{" +
                "id=" + id +
                ", tmplCode='" + tmplCode + '\'' +
                ", tmplName='" + tmplName + '\'' +
                ", tmplDesc='" + tmplDesc + '\'' +
                ", status='" + status + '\'' +
                ", can=" + can +
                ", files=" + files +
                '}';
    }
}
