package com.ruoyi.chain.order.service;

import com.ruoyi.chain.order.domain.ChainOrder;

import java.util.List;
import java.util.Map;

/**
 * 订单Service接口
 *
 * @author Shawn
 * @date 2024-02-26
 */
public interface IChainOrderService {
    /**
     * 查询订单
     *
     * @param id 订单主键
     * @return 订单
     */
    public ChainOrder selectChainOrderById(Long id);

    /**
     * 查询订单列表
     *
     * @param chainOrder 订单
     * @return 订单集合
     */
    public List<ChainOrder> selectChainOrderList(ChainOrder chainOrder);

    /**
     * 新增订单
     *
     * @param chainOrder 订单
     * @return 结果
     */
    public int insertChainOrder(ChainOrder chainOrder);

    /**
     * 修改订单
     *
     * @param chainOrder 订单
     * @return 结果
     */
    public int updateChainOrder(ChainOrder chainOrder);

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteChainOrderByIds(String ids);

    /**
     * 删除订单信息
     *
     * @param id 订单主键
     * @return 结果
     */
    public int deleteChainOrderById(Long id);

    public List<Map> selectOrderStatistics();

}
