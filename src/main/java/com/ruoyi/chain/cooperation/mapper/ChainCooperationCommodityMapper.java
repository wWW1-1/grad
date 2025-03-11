package com.ruoyi.chain.cooperation.mapper;

import com.ruoyi.chain.cooperation.domain.ChainCooperationCommodity;

import java.util.List;

/**
 * 合作公司商品关联Mapper接口
 *
 * @author Shawn
 * @date 2024-02-26
 */
public interface ChainCooperationCommodityMapper {
    /**
     * 查询合作公司商品关联
     *
     * @param id 合作公司商品关联主键
     * @return 合作公司商品关联
     */
    public ChainCooperationCommodity selectChainCooperationCommodityById(Long id);

    /**
     * 查询合作公司商品关联列表
     *
     * @param chainCooperationCommodity 合作公司商品关联
     * @return 合作公司商品关联集合
     */
    public List<ChainCooperationCommodity> selectChainCooperationCommodityList(ChainCooperationCommodity chainCooperationCommodity);

    /**
     * 新增合作公司商品关联
     *
     * @param chainCooperationCommodity 合作公司商品关联
     * @return 结果
     */
    public int insertChainCooperationCommodity(ChainCooperationCommodity chainCooperationCommodity);

    /**
     * 修改合作公司商品关联
     *
     * @param chainCooperationCommodity 合作公司商品关联
     * @return 结果
     */
    public int updateChainCooperationCommodity(ChainCooperationCommodity chainCooperationCommodity);

    /**
     * 删除合作公司商品关联
     *
     * @param id 合作公司商品关联主键
     * @return 结果
     */
    public int deleteChainCooperationCommodityById(Long id);

    /**
     * 批量删除合作公司商品关联
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChainCooperationCommodityByIds(String[] ids);

    List<ChainCooperationCommodity> selectChainCooperationCommodityByCommodityId(Long commodityId);

    List<ChainCooperationCommodity> selectChainCooperationCommodityByCooperationId(Long cooperationId);
}
