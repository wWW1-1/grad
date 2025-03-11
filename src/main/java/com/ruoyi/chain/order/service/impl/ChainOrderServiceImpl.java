package com.ruoyi.chain.order.service.impl;

import com.ruoyi.chain.order.domain.ChainOrder;
import com.ruoyi.chain.order.mapper.ChainOrderMapper;
import com.ruoyi.chain.order.service.IChainOrderService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 订单Service业务层处理
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Service
public class ChainOrderServiceImpl implements IChainOrderService {
    @Autowired
    private ChainOrderMapper chainOrderMapper;

    /**
     * 查询订单
     *
     * @param id 订单主键
     * @return 订单
     */
    @Override
    public ChainOrder selectChainOrderById(Long id) {
        return chainOrderMapper.selectChainOrderById(id);
    }

    /**
     * 查询订单列表
     *
     * @param chainOrder 订单
     * @return 订单
     */
    @Override
    public List<ChainOrder> selectChainOrderList(ChainOrder chainOrder) {
        return chainOrderMapper.selectChainOrderList(chainOrder);
    }

    /**
     * 新增订单
     *
     * @param chainOrder 订单
     * @return 结果
     */
    @Override
    public int insertChainOrder(ChainOrder chainOrder) {
        return chainOrderMapper.insertChainOrder(chainOrder);
    }

    /**
     * 修改订单
     *
     * @param chainOrder 订单
     * @return 结果
     */
    @Override
    public int updateChainOrder(ChainOrder chainOrder) {
        return chainOrderMapper.updateChainOrder(chainOrder);
    }

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteChainOrderByIds(String ids) {
        return chainOrderMapper.deleteChainOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单信息
     *
     * @param id 订单主键
     * @return 结果
     */
    @Override
    public int deleteChainOrderById(Long id) {
        return chainOrderMapper.deleteChainOrderById(id);
    }

    /**
     * 统计订单
     *
     * @return
     */
    @Override
    public List<Map> selectOrderStatistics() {
        return chainOrderMapper.selectOrderStatistics();
    }
}
