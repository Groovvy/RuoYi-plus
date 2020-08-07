package com.baosight.shop.service.impl;

import com.baosight.common.core.service.IPritingService;
import com.baosight.common.domain.CxSelect;
import com.baosight.shop.domain.Address;
import com.baosight.shop.mapper.AddressMapper;
import com.baosight.shop.service.IAddressService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 收货地址Service业务层处理
 *
 * @author Yhn
 * @date 2020-06-11
 */
@Service
public class AddressServiceImpl extends IPritingService implements IAddressService {
    protected final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Resource
    private AddressMapper addressMapper;

    /**
     * 根据ID查询收货地址
     *
     * @param id 收货地址ID
     * @return 收货地址
     */
    @Override
    public Address selectAddressById(Long id) {
        try {
            return addressMapper.selectAddressById(id);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("查询失败", e);
            throw new BusinessException("查询失败!", e);
        }
    }

    /**
     * 查询收货地址列表
     *
     * @param address 收货地址
     * @return 收货地址
     */
    @Override
    public List<Address> selectAddressList(Address address) {
        try {
            return addressMapper.selectAddressList(address);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("查询失败", e);
            throw new BusinessException("查询失败!", e);
        }
    }

    /**
     * 新增收货地址
     *
     * @param address 收货地址
     * @return 结果
     */
    @Override
    public int insertAddress(Address address) {
        try {
            return addressMapper.insertAddress(address);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("新增收货地址失败", e);
            throw new BusinessException("新增收货失败!", e);
        }
    }

    /**
     * 修改收货地址
     *
     * @param address 收货地址
     * @return 结果
     */
    @Override
    public int updateAddress(Address address) {
        try {
            return addressMapper.updateAddress(address);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("修改收货地址失败", e);
            throw new BusinessException("修改收货失败!", e);
        }
    }

    /**
     * 批量删除收货地址信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAddressByIds(String ids) {
        try {
            return addressMapper.deleteAddressByIds(Convert.toStrArray(ids));
        } catch (Exception e) {
            // e.printStackTrace();
            logger.error("批量删除收货地址失败", e);
            throw new BusinessException("批量删除收货失败!", e);
        }
    }

    /**
     * 删除收货地址信息
     *
     * @param id 收货地址ID
     * @return 结果
     */
    @Override
    public int deleteAddressById(Long id) {
        try {
            return addressMapper.deleteAddressById(id);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("删除收货地址失败", e);
            throw new BusinessException("删除收货失败!", e);
        }
    }

    @Override
    public List<CxSelect> seachArea() {
        //查询省
        List<CxSelect> province = addressMapper.seachProvince();
        for (CxSelect p : province) {
            //查询市
            List<CxSelect> city = addressMapper.seachCity(p.getV());
            for (CxSelect c : city) {
                //查询县区
                List<CxSelect> district = addressMapper.seachDistrict(c.getV());
                c.setS(district);
            }
            p.setS(city);
        }
        return province;
    }
}
