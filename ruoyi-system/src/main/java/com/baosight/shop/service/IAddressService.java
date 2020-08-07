package com.baosight.shop.service;

import com.baosight.common.domain.CxSelect;
import com.baosight.shop.domain.Address;

import java.util.List;

/**
 * 收货地址Service接口
 *
 * @author Yhn
 * @date 2020-06-11
 */
public interface IAddressService {
    /**
     * 根据查询收货地址
     *
     * @param id 收货地址
     * @return 收货地址
     */
    public Address selectAddressById(Long id);

    /**
     * 查询收货地址列表
     *
     * @param address 收货地址
     * @return 收货地址集合
     */
    public List<Address> selectAddressList(Address address);

    /**
     * 新增收货地址
     *
     * @param address 收货地址
     * @return 结果
     */
    public int insertAddress(Address address);

    /**
     * 修改收货地址
     *
     * @param address 收货地址
     * @return 结果
     */
    public int updateAddress(Address address);

    /**
     * 批量删除收货地址
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAddressByIds(String ids);

    /**
     * 删除收货地址信息
     *
     * @param id 收货地址ID
     * @return 结果
     */
    public int deleteAddressById(Long id);

    /**
     * 查询全国地址
     *
     * @return 结果
     */
    public List<CxSelect> seachArea();
}
