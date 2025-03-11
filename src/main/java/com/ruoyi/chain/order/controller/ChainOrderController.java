package com.ruoyi.chain.order.controller;

import com.ruoyi.chain.order.domain.ChainOrder;
import com.ruoyi.chain.order.service.IChainOrderService;
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
 * 订单Controller
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Controller
@RequestMapping("/chain/order")
public class ChainOrderController extends BaseController {
    private String prefix = "chain/order";

    @Autowired
    private IChainOrderService chainOrderService;

    @RequiresPermissions("chain:order:view")
    @GetMapping()
    public String order() {
        return prefix + "/order";
    }

    /**
     * 查询订单列表
     */
    @RequiresPermissions("chain:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ChainOrder chainOrder) {
        startPage();
        List<ChainOrder> list = chainOrderService.selectChainOrderList(chainOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @RequiresPermissions("chain:order:export")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ChainOrder chainOrder) {
        List<ChainOrder> list = chainOrderService.selectChainOrderList(chainOrder);
        ExcelUtil<ChainOrder> util = new ExcelUtil<ChainOrder>(ChainOrder.class);
        return util.exportExcel(list, "订单数据");
    }

    /**
     * 新增订单
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }


    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Long id, ModelMap mmap) {
        ChainOrder chainOrder = chainOrderService.selectChainOrderById(id);
        mmap.put("chainOrder", chainOrder);
        return prefix + "/showOrder";
    }

    /**
     * 新增保存订单
     */
    @RequiresPermissions("chain:order:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ChainOrder chainOrder) {
        return toAjax(chainOrderService.insertChainOrder(chainOrder));
    }

    /**
     * 修改订单
     */
    @RequiresPermissions("chain:order:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ChainOrder chainOrder = chainOrderService.selectChainOrderById(id);
        mmap.put("chainOrder", chainOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单
     */
    @RequiresPermissions("chain:order:edit")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ChainOrder chainOrder) {
        return toAjax(chainOrderService.updateChainOrder(chainOrder));
    }

    /**
     * 删除订单
     */
    @RequiresPermissions("chain:order:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(chainOrderService.deleteChainOrderByIds(ids));
    }
}
