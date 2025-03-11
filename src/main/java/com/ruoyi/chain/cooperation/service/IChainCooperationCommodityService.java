package com.ruoyi.chain.cooperation.service;

import com.ruoyi.chain.cooperation.domain.ChainCooperationCommodity;

import java.util.List;

/**
 * 合作公司商品关联Service接口
 *
 * @author Shawn
 * @date 2024-02-26
 */
public interface IChainCooperationCommodityService {
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


    public int updateChainCooperationCommodityUncheck(ChainCooperationCommodity chainCooperationCommodity);

    /**
     * 批量删除合作公司商品关联
     *
     * @param ids 需要删除的合作公司商品关联主键集合
     * @return 结果
     */
    public int deleteChainCooperationCommodityByIds(String ids);

    /**
     * 删除合作公司商品关联信息
     *
     * @param id 合作公司商品关联主键
     * @return 结果
     */
    public int deleteChainCooperationCommodityById(Long id);
}
