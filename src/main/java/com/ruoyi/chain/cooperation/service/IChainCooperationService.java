package com.ruoyi.chain.cooperation.service;

import java.util.List;
import com.ruoyi.chain.cooperation.domain.ChainCooperation;

/**
 * 合作公司Service接口
 * 
 * @author Shawn
 * @date 2024-02-26
 */
public interface IChainCooperationService 
{
    /**
     * 查询合作公司
     * 
     * @param id 合作公司主键
     * @return 合作公司
     */
    public ChainCooperation selectChainCooperationById(Long id);

    /**
     * 查询合作公司列表
     * 
     * @param chainCooperation 合作公司
     * @return 合作公司集合
     */
    public List<ChainCooperation> selectChainCooperationList(ChainCooperation chainCooperation);

    /**
     * 新增合作公司
     * 
     * @param chainCooperation 合作公司
     * @return 结果
     */
    public int insertChainCooperation(ChainCooperation chainCooperation);

    /**
     * 修改合作公司
     * 
     * @param chainCooperation 合作公司
     * @return 结果
     */
    public int updateChainCooperation(ChainCooperation chainCooperation);

    /**
     * 批量删除合作公司
     * 
     * @param ids 需要删除的合作公司主键集合
     * @return 结果
     */
    public int deleteChainCooperationByIds(String ids);

    /**
     * 删除合作公司信息
     * 
     * @param id 合作公司主键
     * @return 结果
     */
    public int deleteChainCooperationById(Long id);
}
