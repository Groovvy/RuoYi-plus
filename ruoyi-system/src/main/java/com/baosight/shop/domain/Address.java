package com.baosight.shop.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 收货地址
 * 对象 t_shop_address
 *
 * @author ruoyi
 * @date 2020-06-11
 */
public class Address extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 地址编号
     */
    private Long id;

    /**
     * 地址代码
     */
    @Excel(name = "地址代码")
    private String code;

    /**
     * 用户名
     */
    @Excel(name = "用户名")
    private String loginName;

    /**
     * 收货人名称
     */
    @Excel(name = "收货人名称")
    private String consignee;

    /**
     * 省份
     */
    @Excel(name = "省份")
    private String province;

    /**
     * 城市
     */
    @Excel(name = "城市")
    private String city;

    /**
     * 区县
     */
    @Excel(name = "区县")
    private String district;

    /**
     * 街道地址
     */
    @Excel(name = "街道地址")
    private String street;

    /**
     * 详细地址
     */
    @Excel(name = "详细地址")
    private String detailAddr;

    /**
     * 邮政编码
     */
    @Excel(name = "邮政编码")
    private String zipcode;

    /**
     * 固定电话
     */
    @Excel(name = "固定电话")
    private String telephone;

    /**
     * 移动电话
     */
    @Excel(name = "移动电话")
    private String mobile;

    /**
     * 邮箱地址
     */
    @Excel(name = "邮箱地址")
    private String email;

    /**
     * 收货地址
     */
    @Excel(name = "收货地址")
    private String address;

    /**
     * 默认收货地址
     */
    @Excel(name = "默认收货地址")
    private String isDefault;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getIsDefault() {
        return isDefault;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("code", getCode())
                .append("loginName", getLoginName())
                .append("consignee", getConsignee())
                .append("province", getProvince())
                .append("city", getCity())
                .append("district", getDistrict())
                .append("street", getStreet())
                .append("detailAddr", getDetailAddr())
                .append("zipcode", getZipcode())
                .append("telephone", getTelephone())
                .append("mobile", getMobile())
                .append("email", getEmail())
                .append("address", getAddress())
                .append("isDefault", getIsDefault())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
