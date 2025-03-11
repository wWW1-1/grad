package com.ruoyi.chain.purchase.controller;

import com.ruoyi.chain.purchase.domain.ChainPurchase;
import com.ruoyi.chain.purchase.service.IChainPurchaseService;
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
 * 采购Controller
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Controller
@RequestMapping("/chain/purchase")
public class ChainPurchaseController extends BaseController {
    private String prefix = "chain/purchase";

    @Autowired
    private IChainPurchaseService chainPurchaseService;

    @RequiresPermissions("chain:purchase:view")
    @GetMapping()
    public String purchase() {
        return prefix + "/purchase";
    }

    /**
     * 查询采购列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ChainPurchase chainPurchase) {
        startPage();
        List<ChainPurchase> list = chainPurchaseService.selectChainPurchaseList(chainPurchase);
        return getDataTable(list);
    }


    @GetMapping("/auditList")
    public String auditList() {
        return prefix + "/purchaseAuditList";
    }


    @GetMapping("/transportList")
    public String transportList() {
        return prefix + "/purchaseTransportList";
    }


    @GetMapping("/audit/{id}")
    public String showAudit(@PathVariable("id") Long id, ModelMap mmap) {
        ChainPurchase chainPurchase = chainPurchaseService.selectChainPurchaseById(id);
        mmap.put("chainPurchase", chainPurchase);
        return prefix + "/purchaseAudit";
    }



    @GetMapping("/transport/{id}")
    public String showTransport(@PathVariable("id") Long id, ModelMap mmap) {
        ChainPurchase chainPurchase = chainPurchaseService.selectChainPurchaseById(id);
        mmap.put("chainPurchase", chainPurchase);
        return prefix + "/purchaseTransport";
    }


    /**
     * 导出采购列表
     */
    @RequiresPermissions("chain:purchase:export")
    @Log(title = "采购", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ChainPurchase chainPurchase) {
        List<ChainPurchase> list = chainPurchaseService.selectChainPurchaseList(chainPurchase);
        ExcelUtil<ChainPurchase> util = new ExcelUtil<ChainPurchase>(ChainPurchase.class);
        return util.exportExcel(list, "采购数据");
    }

    /**
     * 新增采购
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存采购
     */
    @RequiresPermissions("chain:purchase:add")
    @Log(title = "采购", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ChainPurchase chainPurchase) {
        return toAjax(chainPurchaseService.insertChainPurchase(chainPurchase));
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Long id, ModelMap mmap) {
        ChainPurchase chainPurchase = chainPurchaseService.selectChainPurchaseById(id);
        mmap.put("chainPurchase", chainPurchase);
        return prefix + "/showPurchase";
    }

    /**
     * 修改采购
     */
    @RequiresPermissions("chain:purchase:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ChainPurchase chainPurchase = chainPurchaseService.selectChainPurchaseById(id);
        mmap.put("chainPurchase", chainPurchase);
        return prefix + "/edit";
    }

    /**
     * 修改保存采购
     */
    @RequiresPermissions("chain:purchase:edit")
    @Log(title = "采购", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ChainPurchase chainPurchase) {
        chainPurchaseService.checkOperatePurchase(chainPurchase.getId());
        return toAjax(chainPurchaseService.updateChainPurchase(chainPurchase));
    }


    /**
     * 删除采购
     */
    @RequiresPermissions("chain:purchase:remove")
    @Log(title = "采购", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(chainPurchaseService.deleteChainPurchaseByIds(ids));
    }



    @Log(title = "采购审核", businessType = BusinessType.OTHER)
    @PostMapping("/audit")
    @ResponseBody
    public AjaxResult audit(ChainPurchase chainPurchase) {
        int n = chainPurchaseService.audit(chainPurchase);
        return toAjax(n);
    }



    @Log(title = "货运状态", businessType = BusinessType.OTHER)
    @PostMapping("/transport")
    @ResponseBody
    public AjaxResult transport(ChainPurchase chainPurchase) {
        int n = chainPurchaseService.transport(chainPurchase);
        return toAjax(n);
    }

}
