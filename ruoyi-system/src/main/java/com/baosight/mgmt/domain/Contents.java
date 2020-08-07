package com.baosight.mgmt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 内容物管理对象 t_mgmt_contents
 *
 * @author yhn
 * @date 2020-07-02
 */
public class Contents extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 内容物代码
     */
    @Excel(name = "内容物代码")
    private String contCode;

    /**
     * 内容物名称
     */
    @Excel(name = "内容物名称")
    private String contName;

    /**
     * 生产商
     */
    @Excel(name = "生产商")
    private String producer;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String contDesc;

    /**
     * 产地
     */
    @Excel(name = "产地")
    private String factory;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String adress;

    /**
     * 邮编
     */
    @Excel(name = "邮编")
    private String zipcode;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contactPerson;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String contactTel;

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

    public void setContCode(String contCode) {
        this.contCode = contCode;
    }

    public String getContCode() {
        return contCode;
    }

    public void setContName(String contName) {
        this.contName = contName;
    }

    public String getContName() {
        return contName;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProducer() {
        return producer;
    }

    public void setContDesc(String contDesc) {
        this.contDesc = contDesc;
    }

    public String getContDesc() {
        return contDesc;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getFactory() {
        return factory;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAdress() {
        return adress;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactTel() {
        return contactTel;
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
                .append("contCode", getContCode())
                .append("contName", getContName())
                .append("producer", getProducer())
                .append("contDesc", getContDesc())
                .append("factory", getFactory())
                .append("adress", getAdress())
                .append("zipcode", getZipcode())
                .append("contactPerson", getContactPerson())
                .append("contactTel", getContactTel())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
