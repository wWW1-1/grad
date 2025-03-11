package com.ruoyi.chain.sale.mapper;

import com.ruoyi.chain.sale.domain.ChainSale;

import java.util.List;
import java.util.Map;

/**
 * 销售Mapper接口
 *
 * @author Shawn
 * @date 2024-02-26
 */
public interface ChainSaleMapper {
    /**
     * 查询销售
     *
     * @param id 销售主键
     * @return 销售
     */
    public ChainSale selectChainSaleById(Long id);

    /**
     * 查询销售列表
     *
     * @param chainSale 销售
     * @return 销售集合
     */
    public List<ChainSale> selectChainSaleList(ChainSale chainSale);

    /**
     * 新增销售
     *
     * @param chainSale 销售
     * @return 结果
     */
    public int insertChainSale(ChainSale chainSale);

    /**
     * 修改销售
     *
     * @param chainSale 销售
     * @return 结果
     */
    public int updateChainSale(ChainSale chainSale);

    /**
     * 删除销售
     *
     * @param id 销售主键
     * @return 结果
     */
    public int deleteChainSaleById(Long id);

    /**
     * 批量删除销售
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChainSaleByIds(String[] ids);

    List<ChainSale> selectChainSaleByStockId(Long stockId);

    /**
     * 获取最佳销售
     *
     * @return
     */
    List<Map> selectBestSales();

    /**
     * 最佳销售产品
     *
     * @return
     */
    List<Map> selectBestSalesCommodity();

    List<Map> selectSaleStatistics();
}
