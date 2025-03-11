package com.ruoyi.chain.sale.controller;

import com.ruoyi.chain.sale.domain.ChainSale;
import com.ruoyi.chain.sale.service.IChainSaleService;
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
 * 销售Controller
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Controller
@RequestMapping("/chain/sale")
public class ChainSaleController extends BaseController {
    private String prefix = "chain/sale";

    @Autowired
    private IChainSaleService chainSaleService;

    @RequiresPermissions("chain:sale:view")
    @GetMapping()
    public String sale() {
        return prefix + "/sale";
    }


    /**
     * 查询销售列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ChainSale chainSale) {
        startPage();
        List<ChainSale> list = chainSaleService.selectChainSaleList(chainSale);
        return getDataTable(list);
    }


    @GetMapping("/auditList")
    public String auditList() {
        return prefix + "/saleAuditList";
    }



    @GetMapping("/audit/{id}")
    public String showAudit(@PathVariable("id") Long id, ModelMap mmap) {
        ChainSale chainSale = chainSaleService.selectChainSaleById(id);
        mmap.put("chainSale", chainSale);
        return prefix + "/saleAudit";
    }

    /**
     * 导出销售列表
     */
    @RequiresPermissions("chain:sale:export")
    @Log(title = "销售", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ChainSale chainSale) {
        List<ChainSale> list = chainSaleService.selectChainSaleList(chainSale);
        ExcelUtil<ChainSale> util = new ExcelUtil<ChainSale>(ChainSale.class);
        return util.exportExcel(list, "销售数据");
    }


    /**
     * 新增销售
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存销售
     */
    @RequiresPermissions("chain:sale:add")
    @Log(title = "销售", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ChainSale chainSale) {
        return toAjax(chainSaleService.insertChainSale(chainSale));
    }

    /**
     * 修改销售
     */
    @RequiresPermissions("chain:sale:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ChainSale chainSale = chainSaleService.selectChainSaleById(id);
        mmap.put("chainSale", chainSale);
        return prefix + "/edit";
    }

    /**
     * 修改保存销售
     */
    @RequiresPermissions("chain:sale:edit")
    @Log(title = "销售", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ChainSale chainSale) {
        chainSaleService.checkOperateSale(chainSale.getId());
        return toAjax(chainSaleService.updateChainSale(chainSale));
    }

    /**
     * 删除销售
     */
    @RequiresPermissions("chain:sale:remove")
    @Log(title = "销售", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(chainSaleService.deleteChainSaleByIds(ids));
    }



    @Log(title = "销售审核", businessType = BusinessType.OTHER)
    @PostMapping("/audit")
    @ResponseBody
    public AjaxResult audit(ChainSale chainSale) {
        int n = chainSaleService.audit(chainSale);
        return toAjax(n);
    }

}
