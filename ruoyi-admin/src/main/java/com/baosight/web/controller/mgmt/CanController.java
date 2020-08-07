package com.baosight.web.controller.mgmt;

import com.baosight.common.core.controller.IPritingController;
import com.baosight.common.utils.GenerateIdUtil;
import com.baosight.mgmt.domain.Can;
import com.baosight.mgmt.service.ICanService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 罐型管理Controller
 *
 * @author yhn
 * @date 2020-07-02
 */
@Controller
@RequestMapping("/mgmt/can")
public class CanController extends IPritingController {

    protected final Logger logger = LoggerFactory.getLogger(CanController.class);

    private String prefix = "mgmt/can";

    @Autowired
    private ICanService canService;

    @Autowired
    private GenerateIdUtil generateIdUtil;

    @RequiresPermissions("mgmt:can:view")
    @GetMapping()
    public String can() {
        return prefix + "/can";
    }

    /**
     * 查询罐型管理列表
     */
    @RequiresPermissions("mgmt:can:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Can can) {
        startPage();
        List<Can> list = canService.selectCanList(can);
        return getDataTable(list);
    }

    /**
     * 导出罐型管理列表
     */
    @RequiresPermissions("mgmt:can:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Can can) {
        List<Can> list = canService.selectCanList(can);
        ExcelUtil<Can> util = new ExcelUtil<Can>(Can.class);
        return util.exportExcel(list, "can");
    }

    /**
     * 新增罐型管理
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存罐型管理
     */
    @RequiresPermissions("mgmt:can:add")
    @Log(title = "罐型管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Can can) {
        logger.info("controller新增罐型开始");
        // 获取当前的用户信息
        SysUser sysUser = ShiroUtils.getSysUser();
        //生成随机地址罐型code
        can.setCanCode(generateIdUtil.generateContId());
        //01启用;00停用
        can.setStatus("01");
        //创建人
        can.setCreateBy(sysUser.getLoginName());
        can.setUpdateBy(sysUser.getLoginName());
        logger.info("controller新增罐型结束");
        return toAjax(canService.insertCan(can));
    }

    /**
     * 修改罐型管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Can can = canService.selectCanById(id);
        mmap.put("can", can);
        return prefix + "/edit";
    }

    /**
     * 修改保存罐型管理
     */
    @RequiresPermissions("mgmt:can:edit")
    @Log(title = "罐型管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Can can) {
        return toAjax(canService.updateCan(can));
    }

    /**
     * 删除罐型管理
     */
    @RequiresPermissions("mgmt:can:remove")
    @Log(title = "罐型管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(canService.deleteCanByIds(ids));
    }
}
