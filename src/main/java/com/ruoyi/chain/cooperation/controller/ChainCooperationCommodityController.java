package com.ruoyi.chain.cooperation.controller;

import com.ruoyi.chain.cooperation.domain.ChainCooperation;
import com.ruoyi.chain.cooperation.domain.ChainCooperationCommodity;
import com.ruoyi.chain.cooperation.service.IChainCooperationCommodityService;
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
 * 合作公司商品关联Controller
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Controller
@RequestMapping("/chain/cooperationCommodity")
public class ChainCooperationCommodityController extends BaseController {
    private String prefix = "chain/cooperationCommodity";

    @Autowired
    private IChainCooperationCommodityService chainCooperationCommodityService;

    @Autowired
    private IChainCooperationService chainCooperationService;

    @RequiresPermissions("chain:cooperationCommodity:view")
    @GetMapping()
    public String cooperationCommodity(Long cooperationId, ModelMap modelMap) {
        ChainCooperation chainCooperation = null;
        if (null != cooperationId) {
            chainCooperation = chainCooperationService.selectChainCooperationById(cooperationId);
        }
        modelMap.addAttribute("chainCooperation", chainCooperation);
        return prefix + "/cooperationCommodity";
    }


    @GetMapping("/selectCooperationCommodity")
    public String selectCooperationCommodity() {
        return prefix + "/selectCooperationCommodity";
    }


    /**
     * 查询合作公司商品关联列表
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ChainCooperationCommodity chainCooperationCommodity) {
        startPage();
        List<ChainCooperationCommodity> list = chainCooperationCommodityService.selectChainCooperationCommodityList(chainCooperationCommodity);
        return getDataTable(list);
    }

    /**
     * 导出合作公司商品关联列表
     */
    @RequiresPermissions("chain:cooperationCommodity:export")
    @Log(title = "合作公司商品关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ChainCooperationCommodity chainCooperationCommodity) {
        List<ChainCooperationCommodity> list = chainCooperationCommodityService.selectChainCooperationCommodityList(chainCooperationCommodity);
        ExcelUtil<ChainCooperationCommodity> util = new ExcelUtil<ChainCooperationCommodity>(ChainCooperationCommodity.class);
        return util.exportExcel(list, "合作公司商品关联数据");
    }

    /**
     * 新增合作公司商品关联
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存合作公司商品关联
     */
    @RequiresPermissions("chain:cooperationCommodity:add")
    @Log(title = "合作公司商品关联", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ChainCooperationCommodity chainCooperationCommodity) {
        return toAjax(chainCooperationCommodityService.insertChainCooperationCommodity(chainCooperationCommodity));
    }

    /**
     * 修改合作公司商品关联
     */
    @RequiresPermissions("chain:cooperationCommodity:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ChainCooperationCommodity chainCooperationCommodity = chainCooperationCommodityService.selectChainCooperationCommodityById(id);
        mmap.put("chainCooperationCommodity", chainCooperationCommodity);
        return prefix + "/edit";
    }

    /**
     * 修改保存合作公司商品关联
     */
    @RequiresPermissions("chain:cooperationCommodity:edit")
    @Log(title = "合作公司商品关联", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ChainCooperationCommodity chainCooperationCommodity) {
        return toAjax(chainCooperationCommodityService.updateChainCooperationCommodity(chainCooperationCommodity));
    }

    /**
     * 删除合作公司商品关联
     */
    @RequiresPermissions("chain:cooperationCommodity:remove")
    @Log(title = "合作公司商品关联", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(chainCooperationCommodityService.deleteChainCooperationCommodityByIds(ids));
    }
}
