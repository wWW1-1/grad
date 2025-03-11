package com.ruoyi.chain.sale.service.impl;

import com.ruoyi.chain.order.domain.ChainOrder;
import com.ruoyi.chain.order.service.IChainOrderService;
import com.ruoyi.chain.sale.domain.ChainSale;
import com.ruoyi.chain.sale.mapper.ChainSaleMapper;
import com.ruoyi.chain.sale.service.IChainSaleService;
import com.ruoyi.chain.stock.domain.ChainStock;
import com.ruoyi.chain.stock.service.IChainStockService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 销售Service业务层处理
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Service
public class ChainSaleServiceImpl implements IChainSaleService {
    @Autowired
    private ChainSaleMapper chainSaleMapper;

    @Autowired
    private IChainStockService iChainStockService;

    @Autowired
    private IChainOrderService iChainOrderService;

    /**
     * 查询销售
     *
     * @param id 销售主键
     * @return 销售
     */
    @Override
    public ChainSale selectChainSaleById(Long id) {
        return chainSaleMapper.selectChainSaleById(id);
    }

    /**
     * 查询销售列表
     *
     * @param chainSale 销售
     * @return 销售
     */
    @Override
    public List<ChainSale> selectChainSaleList(ChainSale chainSale) {
        return chainSaleMapper.selectChainSaleList(chainSale);
    }

    /**
     * 新增销售
     *
     * @param chainSale 销售
     * @return 结果
     */
    @Override
    public int insertChainSale(ChainSale chainSale) {
        if (chainSale.getQuantity() <= 0) {
            throw new ServiceException("销售数量必须大于0");
        }
        chainSale.setCreatedBy(String.valueOf(ShiroUtils.getUserId()));
        chainSale.setCreatedTime(new Date());
        chainSale.setAuditType(0l);
        //校验商品库存
        checkPurchaseInventory(chainSale);
        return chainSaleMapper.insertChainSale(chainSale);
    }

    /**
     * 修改销售
     *
     * @param chainSale 销售
     * @return 结果
     */
    @Override
    public int updateChainSale(ChainSale chainSale) {
        if (chainSale.getQuantity() <= 0) {
            throw new ServiceException("销售数量必须大于0");
        }
        return chainSaleMapper.updateChainSale(chainSale);
    }

    /**
     * 批量删除销售
     *
     * @param ids 需要删除的销售主键
     * @return 结果
     */
    @Override
    public int deleteChainSaleByIds(String ids) {
        String[] array = Convert.toStrArray(ids);
        for (String id : array) {
            checkOperateSale(Long.parseLong(id));
        }
        return chainSaleMapper.deleteChainSaleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除销售信息
     *
     * @param id 销售主键
     * @return 结果
     */
    @Override
    public int deleteChainSaleById(Long id) {
        return chainSaleMapper.deleteChainSaleById(id);
    }

    @Override
    public int audit(ChainSale chainSale) {
        //如果审核成功
        if (chainSale.getAuditType() == 3) {
            auditSuccess(chainSale);
        }
        return chainSaleMapper.updateChainSale(chainSale);
    }

    /**
     * 审核成功
     *
     * @param chainSale
     */
    private void auditSuccess(ChainSale chainSale) {
        ChainSale sale = chainSaleMapper.selectChainSaleById(chainSale.getId());
        ChainStock chainStock = checkPurchaseInventory(sale);
        if (null != chainStock) {
            chainStock.setQuantity(chainStock.getQuantity() - sale.getQuantity());
            iChainStockService.updateChainStockUnCheck(chainStock);
        }
        //创建订单
        ChainOrder chainOrder = new ChainOrder();
        chainOrder.setAmount(sale.getAmount());
        chainOrder.setSaleId(sale.getId());
        chainOrder.setCommodityId(sale.getCommodityId());
        //出账
        chainOrder.setType(1l);
        chainOrder.setCreatedBy(String.valueOf(ShiroUtils.getUserId()));
        chainOrder.setCreatedTime(new Date());
        iChainOrderService.insertChainOrder(chainOrder);
    }


    /**
     * 校验商品库存
     *
     * @param chainSale
     * @return
     */
    public ChainStock checkPurchaseInventory(ChainSale chainSale) {
        ChainStock chainStock = iChainStockService.selectChainStockById(chainSale.getStockId());
        if (null == chainStock) {
            throw new ServiceException("商品库存不存在，请检查...");
        }
        if (chainStock.getQuantity() < chainSale.getQuantity()) {
            throw new ServiceException("商品库存不足...");
        }
        return chainStock;
    }

    public void checkOperateSale(Long saleId) {
        ChainSale chainSale = chainSaleMapper.selectChainSaleById(saleId);
        if (null == chainSale) {
            throw new ServiceException("销售订单不存在");
        }
        //销售订单已审核通过
        if (chainSale.getAuditType() == 3) {
            throw new ServiceException("销售订单已审核通过不允许操作");
        }
    }

    @Override
    public List<Map> selectBestSales() {
        return chainSaleMapper.selectBestSales();
    }

    @Override
    public List<Map> selectBestSalesCommodity() {
        return chainSaleMapper.selectBestSalesCommodity();
    }

    public List<Map> selectSaleStatistics() {
        return chainSaleMapper.selectSaleStatistics();
    }
}
