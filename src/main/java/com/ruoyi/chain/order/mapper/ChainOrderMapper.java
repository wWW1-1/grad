package com.ruoyi.chain.order.mapper;

import com.ruoyi.chain.order.domain.ChainOrder;

import java.util.List;
import java.util.Map;

/**
 * 订单Mapper接口
 *
 * @author Shawn
 * @date 2024-02-26
 */
public interface ChainOrderMapper {
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
     * 删除订单
     *
     * @param id 订单主键
     * @return 结果
     */
    public int deleteChainOrderById(Long id);

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChainOrderByIds(String[] ids);

    List<ChainOrder> selectChainOrderByCommodityId(Long commodityId);

    /**
     * 统计订单
     *
     * @return
     */
    List<Map> selectOrderStatistics();
}
