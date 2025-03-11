package com.ruoyi.chain.cooperation.service.impl;

import com.ruoyi.chain.cooperation.domain.ChainCooperation;
import com.ruoyi.chain.cooperation.domain.ChainCooperationCommodity;
import com.ruoyi.chain.cooperation.mapper.ChainCooperationCommodityMapper;
import com.ruoyi.chain.cooperation.mapper.ChainCooperationMapper;
import com.ruoyi.chain.cooperation.service.IChainCooperationService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 合作公司Service业务层处理
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Service
public class ChainCooperationServiceImpl implements IChainCooperationService {
    @Autowired
    private ChainCooperationMapper chainCooperationMapper;

    @Autowired
    private ChainCooperationCommodityMapper chainCooperationCommodityMapper;

    /**
     * 查询合作公司
     *
     * @param id 合作公司主键
     * @return 合作公司
     */
    @Override
    public ChainCooperation selectChainCooperationById(Long id) {
        return chainCooperationMapper.selectChainCooperationById(id);
    }

    /**
     * 查询合作公司列表
     *
     * @param chainCooperation 合作公司
     * @return 合作公司
     */
    @Override
    public List<ChainCooperation> selectChainCooperationList(ChainCooperation chainCooperation) {
        return chainCooperationMapper.selectChainCooperationList(chainCooperation);
    }

    /**
     * 新增合作公司
     *
     * @param chainCooperation 合作公司
     * @return 结果
     */
    @Override
    public int insertChainCooperation(ChainCooperation chainCooperation) {
        ChainCooperation cooperation = chainCooperationMapper.selectChainCooperationByName(chainCooperation.getName());
        if (null != cooperation) {
            throw new ServiceException("合作公司已存在不允许添加");
        }

        chainCooperation.setCreatedBy(String.valueOf(ShiroUtils.getUserId()));
        chainCooperation.setCreatedTime(new Date());
        return chainCooperationMapper.insertChainCooperation(chainCooperation);
    }

    /**
     * 修改合作公司
     *
     * @param chainCooperation 合作公司
     * @return 结果
     */
    @Override
    public int updateChainCooperation(ChainCooperation chainCooperation) {
        return chainCooperationMapper.updateChainCooperation(chainCooperation);
    }

    /**
     * 批量删除合作公司
     *
     * @param ids 需要删除的合作公司主键
     * @return 结果
     */
    @Override
    public int deleteChainCooperationByIds(String ids) {
        String[] array = Convert.toStrArray(ids);
        for (String id : array) {
            checkDeleteCooperation(Long.parseLong(id));
        }
        return chainCooperationMapper.deleteChainCooperationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除合作公司信息
     *
     * @param id 合作公司主键
     * @return 结果
     */
    @Override
    public int deleteChainCooperationById(Long id) {
        return chainCooperationMapper.deleteChainCooperationById(id);
    }


    private void checkDeleteCooperation(Long cooperationId) {
        List<ChainCooperationCommodity> chainCooperationCommodityList = chainCooperationCommodityMapper.selectChainCooperationCommodityByCooperationId(cooperationId);
        if (!chainCooperationCommodityList.isEmpty()) {
            throw new ServiceException("合作已被引用不能删除");
        }
    }
}
