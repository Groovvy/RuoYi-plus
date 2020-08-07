package com.baosight.web.controller.mgmt;

import com.baosight.common.core.controller.IPritingController;
import com.baosight.common.utils.GenerateIdUtil;
import com.baosight.mgmt.domain.Contents;
import com.baosight.mgmt.service.IContentsService;
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
 * 内容物管理Controller
 *
 * @author yhn
 * @date 2020-07-02
 */
@Controller
@RequestMapping("/mgmt/contents")
public class ContentsController extends IPritingController {

    protected final Logger logger = LoggerFactory.getLogger(ContentsController.class);

    private String prefix = "mgmt/contents";

    @Autowired
    private IContentsService contentsService;

    @Autowired
    private GenerateIdUtil generateIdUtil;

    @RequiresPermissions("mgmt:contents:view")
    @GetMapping()
    public String contents() {
        return prefix + "/contents";
    }

    /**
     * 查询内容物管理列表
     */
    @RequiresPermissions("mgmt:contents:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Contents contents) {
        startPage();
        List<Contents> list = contentsService.selectContentsList(contents);
        return getDataTable(list);
    }

    /**
     * 导出内容物管理列表
     */
    @RequiresPermissions("mgmt:contents:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Contents contents) {
        List<Contents> list = contentsService.selectContentsList(contents);
        ExcelUtil<Contents> util = new ExcelUtil<Contents>(Contents.class);
        return util.exportExcel(list, "contents");
    }

    /**
     * 新增内容物管理
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存内容物管理
     */
    @RequiresPermissions("mgmt:contents:add")
    @Log(title = "内容物管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Contents contents) {
        logger.info("controller新增内容物开始");
        // 获取当前的用户信息
        SysUser sysUser = ShiroUtils.getSysUser();
        //生成随机地址罐型code
        contents.setContCode(generateIdUtil.generateContId());
        //01启用;00停用
        contents.setStatus("01");
        //创建人
        contents.setCreateBy(sysUser.getLoginName());
        contents.setUpdateBy(sysUser.getLoginName());
        logger.info("controller新增内容物结束");
        return toAjax(contentsService.insertContents(contents));
    }

    /**
     * 修改内容物管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Contents contents = contentsService.selectContentsById(id);
        mmap.put("contents", contents);
        return prefix + "/edit";
    }

    /**
     * 修改保存内容物管理
     */
    @RequiresPermissions("mgmt:contents:edit")
    @Log(title = "内容物管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Contents contents) {
        return toAjax(contentsService.updateContents(contents));
    }

    /**
     * 删除内容物管理
     */
    @RequiresPermissions("mgmt:contents:remove")
    @Log(title = "内容物管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(contentsService.deleteContentsByIds(ids));
    }
}
