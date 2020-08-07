package com.baosight.web.controller.common;

import com.baosight.common.domain.CommonFile;
import com.baosight.common.service.ICommonFileService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 附件公共Controller
 *
 * @author yhn
 * @date 2020-07-07
 */
@Controller
@RequestMapping("/common/file")
public class CommonFileController extends BaseController {
    private String prefix = "common/file";

    @Autowired
    private ICommonFileService commonFileService;

    @RequiresPermissions("common:file:view")
    @GetMapping()
    public String file() {
        return prefix + "/file";
    }

    /**
     * 查询附件公共列表
     */
    @RequiresPermissions("common:file:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CommonFile commonFile) {
        startPage();
        List<CommonFile> list = commonFileService.selectCommonFileList(commonFile);
        return getDataTable(list);
    }

    /**
     * 导出附件公共列表
     */
    @RequiresPermissions("common:file:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CommonFile commonFile) {
        List<CommonFile> list = commonFileService.selectCommonFileList(commonFile);
        ExcelUtil<CommonFile> util = new ExcelUtil<CommonFile>(CommonFile.class);
        return util.exportExcel(list, "file");
    }

    /**
     * 新增附件公共
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存附件公共
     */
    @RequiresPermissions("common:file:add")
    @Log(title = "附件公共", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CommonFile commonFile) {
        return toAjax(commonFileService.insertCommonFile(commonFile));
    }

    /**
     * 修改附件公共
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        CommonFile commonFile = commonFileService.selectCommonFileById(id);
        mmap.put("commonFile", commonFile);
        return prefix + "/edit";
    }

    /**
     * 修改保存附件公共
     */
    @RequiresPermissions("common:file:edit")
    @Log(title = "附件公共", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CommonFile commonFile) {
        return toAjax(commonFileService.updateCommonFile(commonFile));
    }

    /**
     * 删除附件公共
     */
    @RequiresPermissions("common:file:remove")
    @Log(title = "附件公共", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(commonFileService.deleteCommonFileByIds(ids));
    }
}
