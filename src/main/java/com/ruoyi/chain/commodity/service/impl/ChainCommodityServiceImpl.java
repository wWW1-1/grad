package com.ruoyi.chain.commodity.service.impl;

import com.ruoyi.chain.commodity.domain.ChainCommodity;
import com.ruoyi.chain.commodity.mapper.ChainCommodityMapper;
import com.ruoyi.chain.commodity.service.IChainCommodityService;
import com.ruoyi.chain.cooperation.domain.ChainCooperationCommodity;
import com.ruoyi.chain.cooperation.mapper.ChainCooperationCommodityMapper;
import com.ruoyi.chain.order.domain.ChainOrder;
import com.ruoyi.chain.order.mapper.ChainOrderMapper;
import com.ruoyi.chain.stock.domain.ChainStock;
import com.ruoyi.chain.stock.mapper.ChainStockMapper;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品Service业务层处理
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Service
public class ChainCommodityServiceImpl implements IChainCommodityService {
    @Autowired
    private ChainCommodityMapper chainCommodityMapper;

    @Autowired
    private ChainCooperationCommodityMapper chainCooperationCommodityMapper;

    @Autowired
    private ChainOrderMapper chainOrderMapper;

    @Autowired
    private ChainStockMapper chainStockMapper;

    /**
     * 查询商品
     *
     * @param id 商品主键
     * @return 商品
     */
    @Override
    public ChainCommodity selectChainCommodityById(Long id) {
        return chainCommodityMapper.selectChainCommodityById(id);
    }

    /**
     * 查询商品列表
     *
     * @param chainCommodity 商品
     * @return 商品
     */
    @Override
    public List<ChainCommodity> selectChainCommodityList(ChainCommodity chainCommodity) {
        return chainCommodityMapper.selectChainCommodityList(chainCommodity);
    }

    /**
     * 新增商品
     *
     * @param chainCommodity 商品
     * @return 结果
     */
    @Override
    public int insertChainCommodity(ChainCommodity chainCommodity) {
        ChainCommodity commodity = chainCommodityMapper.selectChainCommodityName(chainCommodity.getName());
        if (null != commodity) {
            throw new ServiceException("商品已存在不允许添加");
        }
        chainCommodity.setCreatedBy(String.valueOf(ShiroUtils.getUserId()));
        chainCommodity.setCreatedTime(new Date());
        return chainCommodityMapper.insertChainCommodity(chainCommodity);
    }

    /**
     * 修改商品
     *
     * @param chainCommodity 商品
     * @return 结果
     */
    @Override
    public int updateChainCommodity(ChainCommodity chainCommodity) {
        return chainCommodityMapper.updateChainCommodity(chainCommodity);
    }

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的商品主键
     * @return 结果
     */
    @Override
    public int deleteChainCommodityByIds(String ids) {
        String[] array = Convert.toStrArray(ids);
        for (String id : array) {
            checkDeleteCommodity(Long.parseLong(id));
        }
        return chainCommodityMapper.deleteChainCommodityByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除商品信息
     *
     * @param id 商品主键
     * @return 结果
     */
    @Override
    public int deleteChainCommodityById(Long id) {
        return chainCommodityMapper.deleteChainCommodityById(id);
    }


    private void checkDeleteCommodity(Long commodityId) {
        List<ChainCooperationCommodity> chainCooperationCommodityList = chainCooperationCommodityMapper.selectChainCooperationCommodityByCommodityId(commodityId);
        if (!chainCooperationCommodityList.isEmpty()) {
            throw new ServiceException("商品已被引用不能删除");
        }
        List<ChainOrder> chainOrderList = chainOrderMapper.selectChainOrderByCommodityId(commodityId);
        if (!chainOrderList.isEmpty()) {
            throw new ServiceException("商品已被订单引用不能删除");
        }
        ChainStock chainStock = chainStockMapper.selectChainStockByCommodityId(commodityId);
        if (null != chainStock) {
            throw new ServiceException("商品已被库存引用不能删除");
        }
    }
}
