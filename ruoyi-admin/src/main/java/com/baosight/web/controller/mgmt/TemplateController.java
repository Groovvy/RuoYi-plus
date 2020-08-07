package com.baosight.web.controller.mgmt;

import com.baosight.common.core.controller.IPritingController;
import com.baosight.common.domain.CommonFile;
import com.baosight.common.service.ICommonFileService;
import com.baosight.common.utils.GenerateIdUtil;
import com.baosight.mgmt.domain.Can;
import com.baosight.mgmt.domain.Contents;
import com.baosight.mgmt.domain.Template;
import com.baosight.mgmt.service.ICanService;
import com.baosight.mgmt.service.IContentsService;
import com.baosight.mgmt.service.ITemplateService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.file.FileUploadUtils;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * 模板管理Controller
 *
 * @author yhn
 * @date 2020-06-30
 */
@Controller
@RequestMapping("/mgmt/template")
public class TemplateController extends IPritingController {

    protected final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    private String prefix = "mgmt/template";

    @Autowired
    private ITemplateService templateService;

    @Autowired
    private ICanService canService;

    @Autowired
    private IContentsService contentsService;

    @Autowired
    private GenerateIdUtil generateIdUtil;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private ICommonFileService commonFileService;

    @RequiresPermissions("mgmt:template:view")
    @GetMapping()
    public String template() {
        return prefix + "/template";
    }

    /**
     * 查询模板管理列表
     */
    @RequiresPermissions("mgmt:template:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Template template) {
        startPage();
        List<Template> list = templateService.selectTemplateList(template);
        return getDataTable(list);
    }

    /**
     * 导出模板管理列表
     */
    @RequiresPermissions("mgmt:template:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Template template) {
        List<Template> list = templateService.selectTemplateList(template);
        ExcelUtil<Template> util = new ExcelUtil<Template>(Template.class);
        return util.exportExcel(list, "template");
    }

    /**
     * 新增模板管理
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        Can can = new Can();
        can.setStatus("01");
        Contents contents = new Contents();
        contents.setStatus("01");
        mmap.put("cans", canService.selectCanList(can));
        mmap.put("contents", contentsService.selectContentsList(contents));
        return prefix + "/add";
    }

    /**
     * 新增保存模板管理
     */
    @RequiresPermissions("mgmt:template:add")
    @Log(title = "模板管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody HashMap<String, Object> map) {
        // 获取当前的用户信息
        SysUser sysUser = ShiroUtils.getSysUser();
        map.put("loginName", sysUser.getLoginName());
        //生成随机地址罐型code
        String tmplCode = generateIdUtil.generateTmplId();
        map.put("tmplCode", tmplCode);
        return templateService.insertTemplate(map) > 0 ? AjaxResult.success("保存模板成功", tmplCode) : AjaxResult.error();
    }

    /**
     * 修改模板管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Template template = templateService.selectTemplateById(id);
        mmap.put("template", template);
        return prefix + "/edit";
    }

    /**
     * 修改保存模板管理
     */
    @RequiresPermissions("mgmt:template:edit")
    @Log(title = "模板管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Template template) {
        return toAjax(templateService.updateTemplate(template));
    }

    /**
     * 删除模板管理
     */
    @RequiresPermissions("mgmt:template:remove")
    @Log(title = "模板管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(templateService.deleteTemplateByIds(ids));
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // 上传文件路径
            String filePath = Global.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            //写入file表
            CommonFile commonFile = new CommonFile();
            String bizCode = request.getParameter("bizCode");
            commonFile.setBizCode(bizCode);
            String bizType = request.getParameter("bizType");
            commonFile.setBizType(bizType);
            //原来文件名
            commonFile.setFileName(file.getOriginalFilename());
            //相对路径
            commonFile.setFilePath(fileName);
            // 获取当前的用户信息
            SysUser sysUser = ShiroUtils.getSysUser();
            commonFile.setCreateBy(sysUser.getLoginName());
            commonFile.setUpdateBy(sysUser.getLoginName());
            int rows = commonFileService.insertCommonFile(commonFile);
            AjaxResult ajax = rows > 0 ? AjaxResult.success() : AjaxResult.error();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

}
