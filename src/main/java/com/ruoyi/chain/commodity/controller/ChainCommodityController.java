package com.ruoyi.chain.commodity.controller;

import com.ruoyi.chain.commodity.domain.ChainCommodity;
import com.ruoyi.chain.commodity.service.IChainCommodityService;
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
 * 商品Controller
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Controller
@RequestMapping("/chain/commodity")
public class ChainCommodityController extends BaseController {
    private String prefix = "chain/commodity";

    @Autowired
    private IChainCommodityService chainCommodityService;

    @RequiresPermissions("chain:commodity:view")
    @GetMapping()
    public String commodity() {
        return prefix + "/commodity";
    }


    @GetMapping("/selectCommodity")
    public String selectCommodity() {
        return prefix + "/selectCommodity";
    }


    /**
     * 查询商品列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ChainCommodity chainCommodity) {
        startPage();
        List<ChainCommodity> list = chainCommodityService.selectChainCommodityList(chainCommodity);
        return getDataTable(list);
    }

    /**
     * 导出商品列表
     */
    @RequiresPermissions("chain:commodity:export")
    @Log(title = "商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ChainCommodity chainCommodity) {
        List<ChainCommodity> list = chainCommodityService.selectChainCommodityList(chainCommodity);
        ExcelUtil<ChainCommodity> util = new ExcelUtil<ChainCommodity>(ChainCommodity.class);
        return util.exportExcel(list, "商品数据");
    }

    /**
     * 新增商品
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存商品
     */
    @RequiresPermissions("chain:commodity:add")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ChainCommodity chainCommodity) {
        return toAjax(chainCommodityService.insertChainCommodity(chainCommodity));
    }

    /**
     * 修改商品
     */
    @RequiresPermissions("chain:commodity:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ChainCommodity chainCommodity = chainCommodityService.selectChainCommodityById(id);
        mmap.put("chainCommodity", chainCommodity);
        return prefix + "/edit";
    }

    /**
     * 修改保存商品
     */
    @RequiresPermissions("chain:commodity:edit")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ChainCommodity chainCommodity) {
        return toAjax(chainCommodityService.updateChainCommodity(chainCommodity));
    }

    /**
     * 删除商品
     */
    @RequiresPermissions("chain:commodity:remove")
    @Log(title = "商品", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(chainCommodityService.deleteChainCommodityByIds(ids));
    }
}
