package com.ruoyi.chain.stock.controller;

import com.ruoyi.chain.stock.domain.ChainStock;
import com.ruoyi.chain.stock.service.IChainStockService;
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
 * 库存Controller
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Controller
@RequestMapping("/chain/stock")
public class ChainStockController extends BaseController {
    private String prefix = "chain/stock";

    @Autowired
    private IChainStockService chainStockService;

    @RequiresPermissions("chain:stock:view")
    @GetMapping()
    public String stock() {
        return prefix + "/stock";
    }



    @GetMapping("/selectStock")
    public String selectStock() {
        return prefix + "/selectStock";
    }


    /**
     * 查询库存列表
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ChainStock chainStock) {
        startPage();
        List<ChainStock> list = chainStockService.selectChainStockList(chainStock);
        return getDataTable(list);
    }

    @PostMapping("/availableList")
    @ResponseBody
    public TableDataInfo availableList(ChainStock chainStock) {
        startPage();
        List<ChainStock> list = chainStockService.selectChainStockAvailableList(chainStock);
        return getDataTable(list);
    }

    /**
     * 导出库存列表
     */
    @RequiresPermissions("chain:stock:export")
    @Log(title = "库存", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ChainStock chainStock) {
        List<ChainStock> list = chainStockService.selectChainStockList(chainStock);
        ExcelUtil<ChainStock> util = new ExcelUtil<ChainStock>(ChainStock.class);
        return util.exportExcel(list, "库存数据");
    }

    /**
     * 新增库存
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存库存
     */
    @RequiresPermissions("chain:stock:add")
    @Log(title = "库存", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ChainStock chainStock) {
        return toAjax(chainStockService.insertChainStock(chainStock));
    }

    /**
     * 修改库存
     */
    @RequiresPermissions("chain:stock:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ChainStock chainStock = chainStockService.selectChainStockById(id);
        mmap.put("chainStock", chainStock);
        return prefix + "/edit";
    }

    /**
     * 修改保存库存
     */
    @RequiresPermissions("chain:stock:edit")
    @Log(title = "库存", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ChainStock chainStock) {
        return toAjax(chainStockService.updateChainStock(chainStock));
    }

    /**
     * 删除库存
     */
    @RequiresPermissions("chain:stock:remove")
    @Log(title = "库存", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(chainStockService.deleteChainStockByIds(ids));
    }
}
