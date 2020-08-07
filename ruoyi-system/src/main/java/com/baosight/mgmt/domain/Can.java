package com.baosight.mgmt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 罐型管理对象 t_mgmt_can
 *
 * @author yhn
 * @date 2020-07-02
 */
public class Can extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 罐型代码
     */
    @Excel(name = "罐型代码")
    private String canCode;

    /**
     * 类型/规格
     */
    @Excel(name = "类型/规格")
    private String canStyle;

    /**
     * 缩颈线
     */
    @Excel(name = "缩颈线")
    private Long neckline;

    /**
     * 罐型宽度
     */
    @Excel(name = "罐型宽度")
    private Long width;

    /**
     * 罐型高度
     */
    @Excel(name = "罐型高度")
    private Long height;

    /**
     * 3D的地址
     */
    @Excel(name = "3D的地址")
    private String modelUrl;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCanCode(String canCode) {
        this.canCode = canCode;
    }

    public String getCanCode() {
        return canCode;
    }

    public void setCanStyle(String canStyle) {
        this.canStyle = canStyle;
    }

    public String getCanStyle() {
        return canStyle;
    }

    public void setNeckline(Long neckline) {
        this.neckline = neckline;
    }

    public Long getNeckline() {
        return neckline;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getWidth() {
        return width;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getHeight() {
        return height;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }

    public String getModelUrl() {
        return modelUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("canCode", getCanCode())
                .append("canStyle", getCanStyle())
                .append("neckline", getNeckline())
                .append("width", getWidth())
                .append("height", getHeight())
                .append("modelUrl", getModelUrl())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
