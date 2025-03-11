package com.ruoyi.chain.stock.mapper;

import com.ruoyi.chain.stock.domain.ChainStock;

import java.util.List;

/**
 * 库存Mapper接口
 *
 * @author Shawn
 * @date 2024-02-26
 */
public interface ChainStockMapper {
    /**
     * 查询库存
     *
     * @param id 库存主键
     * @return 库存
     */
    public ChainStock selectChainStockById(Long id);

    /**
     * 查询库存列表
     *
     * @param chainStock 库存
     * @return 库存集合
     */
    public List<ChainStock> selectChainStockList(ChainStock chainStock);

    /**
     * 新增库存
     *
     * @param chainStock 库存
     * @return 结果
     */
    public int insertChainStock(ChainStock chainStock);

    /**
     * 修改库存
     *
     * @param chainStock 库存
     * @return 结果
     */
    public int updateChainStock(ChainStock chainStock);

    /**
     * 删除库存
     *
     * @param id 库存主键
     * @return 结果
     */
    public int deleteChainStockById(Long id);

    /**
     * 批量删除库存
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChainStockByIds(String[] ids);

    public ChainStock selectChainStockByCommodityId(Long commodityId);

    public List<ChainStock> selectChainStockAvailableList(ChainStock chainStock);
}
