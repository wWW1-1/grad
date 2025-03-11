package com.ruoyi.chain.stock.service.impl;

import com.ruoyi.chain.sale.domain.ChainSale;
import com.ruoyi.chain.sale.mapper.ChainSaleMapper;
import com.ruoyi.chain.stock.domain.ChainStock;
import com.ruoyi.chain.stock.mapper.ChainStockMapper;
import com.ruoyi.chain.stock.service.IChainStockService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 库存Service业务层处理
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Service
public class ChainStockServiceImpl implements IChainStockService {
    @Autowired
    private ChainStockMapper chainStockMapper;

    @Autowired
    private ChainSaleMapper chainSaleMapper;

    /**
     * 查询库存
     *
     * @param id 库存主键
     * @return 库存
     */
    @Override
    public ChainStock selectChainStockById(Long id) {
        return chainStockMapper.selectChainStockById(id);
    }

    /**
     * 查询库存列表
     *
     * @param chainStock 库存
     * @return 库存
     */
    @Override
    public List<ChainStock> selectChainStockList(ChainStock chainStock) {
        return chainStockMapper.selectChainStockList(chainStock);
    }

    /**
     * 新增库存
     *
     * @param chainStock 库存
     * @return 结果
     */
    @Override
    public int insertChainStock(ChainStock chainStock) {
        if (chainStock.getQuantity() <= 0) {
            throw new ServiceException("库存数量必须大于0");
        }
        chainStock.setCreatedBy(String.valueOf(ShiroUtils.getUserId()));
        chainStock.setCreatedTime(new Date());
        return chainStockMapper.insertChainStock(chainStock);
    }

    /**
     * 修改库存
     *
     * @param chainStock 库存
     * @return 结果
     */
    @Override
    public int updateChainStock(ChainStock chainStock) {
        if (chainStock.getQuantity() <= 0) {
            throw new ServiceException("库存数量必须大于0");
        }
        return chainStockMapper.updateChainStock(chainStock);
    }

    public int updateChainStockUnCheck(ChainStock chainStock) {
        return chainStockMapper.updateChainStock(chainStock);
    }

    public void updateInventory(long commodityId, long quantity) {
        ChainStock chainStock = chainStockMapper.selectChainStockByCommodityId(commodityId);
        //新增库存
        if (null == chainStock) {
            chainStock = new ChainStock();
            chainStock.setCommodityId(commodityId);
            chainStock.setQuantity(quantity);
            chainStock.setStatus(0l);
            chainStock.setCreatedBy(String.valueOf(ShiroUtils.getUserId()));
            chainStock.setCreatedTime(new Date());
            chainStockMapper.insertChainStock(chainStock);
        } else {
            chainStock.setQuantity(chainStock.getQuantity() + quantity);
            chainStockMapper.updateChainStock(chainStock);
        }
    }

    /**
     * 批量删除库存
     *
     * @param ids 需要删除的库存主键
     * @return 结果
     */
    @Override
    public int deleteChainStockByIds(String ids) {
        String[] array = Convert.toStrArray(ids);
        for (String id : array) {
            checkDeleteStock(Long.parseLong(id));
        }
        return chainStockMapper.deleteChainStockByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除库存信息
     *
     * @param id 库存主键
     * @return 结果
     */
    @Override
    public int deleteChainStockById(Long id) {
        return chainStockMapper.deleteChainStockById(id);
    }

    @Override
    public ChainStock selectChainStockByCommodityId(Long commodityId) {
        return chainStockMapper.selectChainStockByCommodityId(commodityId);
    }

    @Override
    public List<ChainStock> selectChainStockAvailableList(ChainStock chainStock) {
        return chainStockMapper.selectChainStockAvailableList(chainStock);
    }


    public void checkDeleteStock(Long stockId) {
        List<ChainSale> chainSaleList = chainSaleMapper.selectChainSaleByStockId(stockId);
        if (!chainSaleList.isEmpty()) {
            throw new ServiceException("库存已被引用，不允许删除");
        }
    }
}
