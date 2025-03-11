package com.ruoyi.chain.cooperation.service.impl;

import com.ruoyi.chain.cooperation.domain.ChainCooperationCommodity;
import com.ruoyi.chain.cooperation.mapper.ChainCooperationCommodityMapper;
import com.ruoyi.chain.cooperation.service.IChainCooperationCommodityService;
import com.ruoyi.chain.purchase.mapper.ChainPurchaseMapper;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 合作公司商品关联Service业务层处理
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Service
public class ChainCooperationCommodityServiceImpl implements IChainCooperationCommodityService {
    @Autowired
    private ChainCooperationCommodityMapper chainCooperationCommodityMapper;

    @Autowired
    private ChainPurchaseMapper chainPurchaseMapper;

    /**
     * 查询合作公司商品关联
     *
     * @param id 合作公司商品关联主键
     * @return 合作公司商品关联
     */
    @Override
    public ChainCooperationCommodity selectChainCooperationCommodityById(Long id) {
        return chainCooperationCommodityMapper.selectChainCooperationCommodityById(id);
    }

    /**
     * 查询合作公司商品关联列表
     *
     * @param chainCooperationCommodity 合作公司商品关联
     * @return 合作公司商品关联
     */
    @Override
    public List<ChainCooperationCommodity> selectChainCooperationCommodityList(ChainCooperationCommodity chainCooperationCommodity) {
        return chainCooperationCommodityMapper.selectChainCooperationCommodityList(chainCooperationCommodity);
    }

    /**
     * 新增合作公司商品关联
     *
     * @param chainCooperationCommodity 合作公司商品关联
     * @return 结果
     */
    @Override
    public int insertChainCooperationCommodity(ChainCooperationCommodity chainCooperationCommodity) {
        if (chainCooperationCommodity.getQuantity() <= 0) {
            throw new ServiceException("商品数量必须大于0");
        }
        chainCooperationCommodity.setCreatedBy(String.valueOf(ShiroUtils.getUserId()));
        chainCooperationCommodity.setCreatedTime(new Date());
        //重复检查
        checkRepeat(chainCooperationCommodity);

        return chainCooperationCommodityMapper.insertChainCooperationCommodity(chainCooperationCommodity);
    }

    /**
     * 检查重复
     *
     * @param chainCooperationCommodity
     */
    private void checkRepeat(ChainCooperationCommodity chainCooperationCommodity) {
        ChainCooperationCommodity tmp = new ChainCooperationCommodity();
        tmp.setCooperationId(chainCooperationCommodity.getCooperationId());
        tmp.setCommodityId(chainCooperationCommodity.getCommodityId());
        List<ChainCooperationCommodity> chainCooperationCommodityList = chainCooperationCommodityMapper.selectChainCooperationCommodityList(tmp);
        if (!chainCooperationCommodityList.isEmpty()) {
            throw new ServiceException("商品重复不允许添加");
        }
    }

    /**
     * 修改合作公司商品关联
     *
     * @param chainCooperationCommodity 合作公司商品关联
     * @return 结果
     */
    @Override
    public int updateChainCooperationCommodity(ChainCooperationCommodity chainCooperationCommodity) {
        if (chainCooperationCommodity.getQuantity() <= 0) {
            throw new ServiceException("商品数量必须大于0");
        }
        return chainCooperationCommodityMapper.updateChainCooperationCommodity(chainCooperationCommodity);
    }


    public int updateChainCooperationCommodityUncheck(ChainCooperationCommodity chainCooperationCommodity) {
        return chainCooperationCommodityMapper.updateChainCooperationCommodity(chainCooperationCommodity);
    }

    /**
     * 批量删除合作公司商品关联
     *
     * @param ids 需要删除的合作公司商品关联主键
     * @return 结果
     */
    @Override
    public int deleteChainCooperationCommodityByIds(String ids) {
        String[] array = Convert.toStrArray(ids);
        for (String id : array) {
            checkDeleteCooperationCommodity(Long.parseLong(id));
        }
        return chainCooperationCommodityMapper.deleteChainCooperationCommodityByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除合作公司商品关联信息
     *
     * @param id 合作公司商品关联主键
     * @return 结果
     */
    @Override
    public int deleteChainCooperationCommodityById(Long id) {
        return chainCooperationCommodityMapper.deleteChainCooperationCommodityById(id);
    }

    private void checkDeleteCooperationCommodity(Long cooperationCommodityId) {
        List<ChainCooperationCommodity> chainCooperationCommodityList = chainPurchaseMapper.selectChainPurchaseByCooperationCommodityId(cooperationCommodityId);
        if (!chainCooperationCommodityList.isEmpty()) {
            throw new ServiceException("商品已经被引用不能删除");
        }
    }
}
