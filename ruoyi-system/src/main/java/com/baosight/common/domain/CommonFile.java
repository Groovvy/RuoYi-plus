package com.baosight.common.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 附件公共对象 t_common_file
 *
 * @author yhn
 * @date 2020-07-07
 */
public class CommonFile extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 业务代码
     */
    @Excel(name = "业务代码")
    private String bizCode;

    /**
     * 业务分类
     */
    @Excel(name = "业务分类")
    private String bizType;

    /**
     * 文件名
     */
    @Excel(name = "文件名")
    private String fileName;

    /**
     * 文件路径
     */
    @Excel(name = "文件路径")
    private String filePath;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizType() {
        return bizType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("bizCode", getBizCode())
                .append("bizType", getBizType())
                .append("fileName", getFileName())
                .append("filePath", getFilePath())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
