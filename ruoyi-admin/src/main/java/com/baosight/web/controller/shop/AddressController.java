package com.baosight.web.controller.shop;

import com.baosight.common.core.controller.IPritingController;
import com.baosight.common.utils.GenerateIdUtil;
import com.baosight.shop.domain.Address;
import com.baosight.shop.service.IAddressService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收货地址Controller
 *
 * @author yhn
 * @date 2020-06-11
 */
@Api(value = "用户地址信息管理")
@Controller
@RequestMapping("/shop/address")
public class AddressController extends IPritingController {

    protected final Logger logger = LoggerFactory.getLogger(AddressController.class);

    private String prefix = "shop/address";

    @Autowired
    private IAddressService addressService;

    @Autowired
    private GenerateIdUtil generateIdUtil;

    @RequiresPermissions("shop:address:view")
    @GetMapping()
    public String address() {
        return prefix + "/address";
    }

    /**
     * 查询我的收货地址
     * 列表
     */
    @ApiOperation(value = "查询收货地址", notes = "个人收货地址")
    @RequiresPermissions("shop:address:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Address address) {
        logger.info("Controller查询我的收货地址开始");
        // 获取当前的用户信息
        SysUser sysUser = ShiroUtils.getSysUser();
        address.setLoginName(sysUser.getLoginName());
        startPage();
        List<Address> list = addressService.selectAddressList(address);
        logger.info("Controller查询我的收货地址结束");
        //return AjaxResult.success(list);
        return getDataTable(list);
    }


    /**
     * 新增收货地址
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        //地址下拉框三级联动
        //List<CxSelect> data = addressService.seachArea();
        //mmap.put("data", JSON.toJSON(data));
        return prefix + "/add";
    }

    /**
     * 新增保存收货地址
     */
    @ApiOperation(value = "新增收货地址", notes = "新增个人收货地址")
    @RequiresPermissions("shop:address:add")
    @Log(title = "收货地址", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Address address) {
        logger.info("Controller新增收货地址开始");
        // 获取当前的用户信息
        SysUser sysUser = ShiroUtils.getSysUser();
        address.setLoginName(sysUser.getLoginName());
        //生成随机地址code
        address.setCode(generateIdUtil.generateAdressId());
        //创建人
        address.setCreateBy(sysUser.getLoginName());
        address.setUpdateBy(sysUser.getLoginName());
        logger.info("Controller新增收货地址结束");
        return toAjax(addressService.insertAddress(address));//统一异常处理自定义异常
    }

    /**
     * 修改收货地址
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Address address = addressService.selectAddressById(id);
        mmap.put("address", address);
        return prefix + "/edit";
    }

    /**
     * 修改保存收货地址
     */
    @RequiresPermissions("shop:address:edit")
    @Log(title = "收货地址", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Address address) {
        logger.info("Controller修改收货地址开始");
        // 获取当前的用户信息
        SysUser sysUser = ShiroUtils.getSysUser();
        //修改人
        address.setUpdateBy(sysUser.getLoginName());
        logger.info("Controller新增收货地址结束");
        return toAjax(addressService.updateAddress(address));
    }

    /**
     * 批量删除收货地址
     */
    @RequiresPermissions("shop:address:remove")
    @Log(title = "收货地址", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        logger.info("Controller批量删除收货地址开始");
        return toAjax(addressService.deleteAddressByIds(ids));
    }
}
