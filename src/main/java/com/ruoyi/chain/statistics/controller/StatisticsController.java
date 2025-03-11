package com.ruoyi.chain.statistics.controller;

import com.ruoyi.chain.purchase.service.IChainPurchaseService;
import com.ruoyi.chain.sale.service.IChainSaleService;
import com.ruoyi.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/chain/statistics")
public class StatisticsController extends BaseController {
    private String prefix = "chain/statistics";

    @Autowired
    private IChainSaleService iChainSaleService;
    @Autowired
    private IChainPurchaseService iChainPurchaseService;


    @GetMapping()
    public String statistics(ModelMap mmap) {
        List<Map> bestSalesList = iChainSaleService.selectBestSales();
        List<Map> bestSalesCommodityList = iChainSaleService.selectBestSalesCommodity();
        List<Map> bestPurchaseList = iChainPurchaseService.selectBestPurchase();

        mmap.addAttribute("bestSalesList", bestSalesList);
        mmap.addAttribute("bestPurchaseList", bestPurchaseList);
        mmap.addAttribute("bestSalesCommodityList", bestSalesCommodityList);
        return prefix + "/statistics";
    }
}
