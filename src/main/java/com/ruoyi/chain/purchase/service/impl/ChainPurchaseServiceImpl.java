package com.ruoyi.chain.purchase.service.impl;

import com.ruoyi.chain.cooperation.domain.ChainCooperationCommodity;
import com.ruoyi.chain.cooperation.service.IChainCooperationCommodityService;
import com.ruoyi.chain.order.domain.ChainOrder;
import com.ruoyi.chain.order.service.IChainOrderService;
import com.ruoyi.chain.purchase.domain.ChainPurchase;
import com.ruoyi.chain.purchase.mapper.ChainPurchaseMapper;
import com.ruoyi.chain.purchase.service.IChainPurchaseService;
import com.ruoyi.chain.stock.service.IChainStockService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采购Service业务层处理
 *
 * @author Shawn
 * @date 2024-02-26
 */
@Service
public class ChainPurchaseServiceImpl implements IChainPurchaseService {
    @Autowired
    private ChainPurchaseMapper chainPurchaseMapper;

    @Autowired
    private IChainStockService iChainStockService;

    @Autowired
    private IChainOrderService iChainOrderService;

    @Autowired
    private IChainCooperationCommodityService iChainCooperationCommodityService;

    /**
     * 查询采购
     *
     * @param id 采购主键
     * @return 采购
     */
    @Override
    public ChainPurchase selectChainPurchaseById(Long id) {
        return chainPurchaseMapper.selectChainPurchaseById(id);
    }

    /**
     * 查询采购列表
     *
     * @param chainPurchase 采购
     * @return 采购
     */
    @Override
    public List<ChainPurchase> selectChainPurchaseList(ChainPurchase chainPurchase) {
        return chainPurchaseMapper.selectChainPurchaseList(chainPurchase);
    }

    /**
     * 新增采购
     *
     * @param chainPurchase 采购
     * @return 结果
     */
    @Override
    public int insertChainPurchase(ChainPurchase chainPurchase) {
        if (chainPurchase.getQuantity() <= 0) {
            throw new ServiceException("采购数量必须大于0");
        }
        chainPurchase.setCreatedBy(String.valueOf(ShiroUtils.getUserId()));
        chainPurchase.setCreatedTime(new Date());
        chainPurchase.setAuditType(0l);
        chainPurchase.setTransportStatus(1l);
        checkCommodityInventory(chainPurchase);
        return chainPurchaseMapper.insertChainPurchase(chainPurchase);
    }

    /**
     * 修改采购
     *
     * @param chainPurchase 采购
     * @return 结果
     */
    @Override
    public int updateChainPurchase(ChainPurchase chainPurchase) {
        if (chainPurchase.getQuantity() <= 0) {
            throw new ServiceException("采购数量必须大于0");
        }
        return chainPurchaseMapper.updateChainPurchase(chainPurchase);
    }

    /**
     * 批量删除采购
     *
     * @param ids 需要删除的采购主键
     * @return 结果
     */
    @Override
    public int deleteChainPurchaseByIds(String ids) {
        String[] array = Convert.toStrArray(ids);
        for (String id : array) {
            checkOperatePurchase(Long.parseLong(id));
        }
        return chainPurchaseMapper.deleteChainPurchaseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除采购信息
     *
     * @param id 采购主键
     * @return 结果
     */
    @Override
    public int deleteChainPurchaseById(Long id) {
        return chainPurchaseMapper.deleteChainPurchaseById(id);
    }

    @Override
    public int audit(ChainPurchase chainPurchase) {
        //如果审核成功
        if (chainPurchase.getAuditType() == 3) {
            auditSuccess(chainPurchase);
        }
        return chainPurchaseMapper.updateChainPurchase(chainPurchase);
    }

    /**
     * 审核通过
     *
     * @param chainPurchase
     */
    private void auditSuccess(ChainPurchase chainPurchase) {
        ChainPurchase purchase = chainPurchaseMapper.selectChainPurchaseById(chainPurchase.getId());
        ChainCooperationCommodity cooperationCommodity = checkCommodityInventory(purchase);
        if (null != cooperationCommodity) {
            cooperationCommodity.setQuantity(cooperationCommodity.getQuantity() - purchase.getQuantity());
            cooperationCommodity.setCreatedBy(null);
            iChainCooperationCommodityService.updateChainCooperationCommodityUncheck(cooperationCommodity);
        }
        //创建订单
        ChainOrder chainOrder = new ChainOrder();
        chainOrder.setAmount(purchase.getAmount());
        chainOrder.setPurchaseId(purchase.getId());
        chainOrder.setCommodityId(purchase.getCommodityId());
        //出账
        chainOrder.setType(2l);
        chainOrder.setCreatedBy(String.valueOf(ShiroUtils.getUserId()));
        chainOrder.setCreatedTime(new Date());
        iChainOrderService.insertChainOrder(chainOrder);
    }

    @Override
    public int transport(ChainPurchase chainPurchase) {
        //商品已送达
        if (chainPurchase.getTransportStatus() == 4) {
            ChainPurchase purchase = chainPurchaseMapper.selectChainPurchaseById(chainPurchase.getId());
            if (null == purchase) {
                throw new ServiceException("采购数据不存在");
            }
            iChainStockService.updateInventory(purchase.getCommodityId(), purchase.getQuantity());
        }
        return chainPurchaseMapper.updateChainPurchase(chainPurchase);
    }

    private ChainCooperationCommodity checkCommodityInventory(ChainPurchase chainPurchase) {
        ChainCooperationCommodity cooperationCommodity = iChainCooperationCommodityService.selectChainCooperationCommodityById(chainPurchase.getCooCommodityId());
        if (null == cooperationCommodity) {
            throw new ServiceException("商品不存在");
        }
        if (cooperationCommodity.getQuantity() < chainPurchase.getQuantity()) {
            throw new ServiceException("商品库存不足");
        }
        return cooperationCommodity;
    }


    public void checkOperatePurchase(Long purchaseId) {
        ChainPurchase chainPurchase = chainPurchaseMapper.selectChainPurchaseById(purchaseId);
        if (null == chainPurchase) {
            throw new ServiceException("采购单不存在");
        }
        //如果审核通过
        if (chainPurchase.getAuditType() == 3) {
            throw new ServiceException("采购单已审核通过，不允许操作");
        }
    }

    public List<Map> selectBestPurchase() {
        return chainPurchaseMapper.selectBestPurchase();
    }

    public List<Map> selectPurchaseStatistics() {
        return chainPurchaseMapper.selectPurchaseStatistics();
    }

    public List<Map> selectPurchasetTansportStatus() {
        return chainPurchaseMapper.selectPurchasetTansportStatus();
    }
}
