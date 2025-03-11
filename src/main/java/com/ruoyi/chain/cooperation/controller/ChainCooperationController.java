package com.ruoyi.chain.cooperation.controller;

import com.ruoyi.chain.cooperation.domain.ChainCooperation;
import com.ruoyi.chain.cooperation.service.IChainCooperationService;
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
 * 合作公司Controller
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Controller
@RequestMapping("/chain/cooperation")
public class ChainCooperationController extends BaseController {
    private String prefix = "chain/cooperation";

    @Autowired
    private IChainCooperationService chainCooperationService;

    @RequiresPermissions("chain:cooperation:view")
    @GetMapping()
    public String cooperation() {
        return prefix + "/cooperation";
    }


    @RequiresPermissions("chain:cooperation:view")
    @GetMapping("/selectCooperation")
    public String selectCooperation() {
        return prefix + "/selectCooperation";
    }


    /**
     * 查询合作公司列表
     */
    @RequiresPermissions("chain:cooperation:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ChainCooperation chainCooperation) {
        startPage();
        List<ChainCooperation> list = chainCooperationService.selectChainCooperationList(chainCooperation);
        return getDataTable(list);
    }

    /**
     * 导出合作公司列表
     */
    @RequiresPermissions("chain:cooperation:export")
    @Log(title = "合作公司", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ChainCooperation chainCooperation) {
        List<ChainCooperation> list = chainCooperationService.selectChainCooperationList(chainCooperation);
        ExcelUtil<ChainCooperation> util = new ExcelUtil<ChainCooperation>(ChainCooperation.class);
        return util.exportExcel(list, "合作公司数据");
    }

    /**
     * 新增合作公司
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存合作公司
     */
    @RequiresPermissions("chain:cooperation:add")
    @Log(title = "合作公司", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ChainCooperation chainCooperation) {
        return toAjax(chainCooperationService.insertChainCooperation(chainCooperation));
    }

    /**
     * 修改合作公司
     */
    @RequiresPermissions("chain:cooperation:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ChainCooperation chainCooperation = chainCooperationService.selectChainCooperationById(id);
        mmap.put("chainCooperation", chainCooperation);
        return prefix + "/edit";
    }

    /**
     * 修改保存合作公司
     */
    @RequiresPermissions("chain:cooperation:edit")
    @Log(title = "合作公司", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ChainCooperation chainCooperation) {
        return toAjax(chainCooperationService.updateChainCooperation(chainCooperation));
    }

    /**
     * 删除合作公司
     */
    @RequiresPermissions("chain:cooperation:remove")
    @Log(title = "合作公司", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(chainCooperationService.deleteChainCooperationByIds(ids));
    }
}
