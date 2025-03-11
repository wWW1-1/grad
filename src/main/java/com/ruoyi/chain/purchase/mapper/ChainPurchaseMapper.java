package com.ruoyi.chain.purchase.mapper;

import com.ruoyi.chain.cooperation.domain.ChainCooperationCommodity;
import com.ruoyi.chain.purchase.domain.ChainPurchase;

import java.util.List;
import java.util.Map;

/**
 * 采购Mapper接口
 *
 * @author Shawn
 * @date 2024-02-26
 */
public interface ChainPurchaseMapper {
    /**
     * 查询采购
     *
     * @param id 采购主键
     * @return 采购
     */
    public ChainPurchase selectChainPurchaseById(Long id);

    /**
     * 查询采购列表
     *
     * @param chainPurchase 采购
     * @return 采购集合
     */
    public List<ChainPurchase> selectChainPurchaseList(ChainPurchase chainPurchase);

    /**
     * 新增采购
     *
     * @param chainPurchase 采购
     * @return 结果
     */
    public int insertChainPurchase(ChainPurchase chainPurchase);

    /**
     * 修改采购
     *
     * @param chainPurchase 采购
     * @return 结果
     */
    public int updateChainPurchase(ChainPurchase chainPurchase);

    /**
     * 删除采购
     *
     * @param id 采购主键
     * @return 结果
     */
    public int deleteChainPurchaseById(Long id);

    /**
     * 批量删除采购
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChainPurchaseByIds(String[] ids);

    List<ChainCooperationCommodity> selectChainPurchaseByCooperationCommodityId(Long cooperationCommodityId);

    /**
     * 获取最佳采购
     *
     * @return
     */
    public List<Map> selectBestPurchase();

    public List<Map> selectPurchaseStatistics();

    public List<Map> selectPurchasetTansportStatus();

}
