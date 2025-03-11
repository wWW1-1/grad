package com.ruoyi.chain.commodity.mapper;

import java.util.List;
import com.ruoyi.chain.commodity.domain.ChainCommodity;

/**
 * 商品Mapper接口
 * 
 * @author Shawn
 * @date 2024-02-26
 */
public interface ChainCommodityMapper 
{
    /**
     * 查询商品
     * 
     * @param id 商品主键
     * @return 商品
     */
    public ChainCommodity selectChainCommodityById(Long id);

    /**
     * 查询商品列表
     * 
     * @param chainCommodity 商品
     * @return 商品集合
     */
    public List<ChainCommodity> selectChainCommodityList(ChainCommodity chainCommodity);

    /**
     * 新增商品
     * 
     * @param chainCommodity 商品
     * @return 结果
     */
    public int insertChainCommodity(ChainCommodity chainCommodity);

    /**
     * 修改商品
     * 
     * @param chainCommodity 商品
     * @return 结果
     */
    public int updateChainCommodity(ChainCommodity chainCommodity);

    /**
     * 删除商品
     * 
     * @param id 商品主键
     * @return 结果
     */
    public int deleteChainCommodityById(Long id);

    /**
     * 批量删除商品
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChainCommodityByIds(String[] ids);

    ChainCommodity selectChainCommodityName(String name);
}
