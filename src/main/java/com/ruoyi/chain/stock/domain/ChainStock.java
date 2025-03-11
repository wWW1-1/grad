package com.ruoyi.chain.stock.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存对象 t_chain_stock
 *
 * @author Shawn
 * @date 2024-02-26
 */
public class ChainStock extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商品库存ID
     */
    private Long id;

    /**
     * 商品ID
     */
    @Excel(name = "商品ID")
    private Long commodityId;

    private String commodityName;


    private String image;

    /**
     * 库存数量
     */
    @Excel(name = "库存数量")
    private Long quantity;

    /**
     * 进货价格
     */
    @Excel(name = "进货价格")
    private BigDecimal purchasePrice;

    /**
     * 零售价
     */
    @Excel(name = "零售价")
    private BigDecimal retailPrice;

    /**
     * 摆放位置
     */
    @Excel(name = "摆放位置")
    private String position;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Long status;

    /**
     * 乐观锁
     */
    private Long REVISION;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStatus() {
        return status;
    }

    public void setREVISION(Long REVISION) {
        this.REVISION = REVISION;
    }

    public Long getREVISION() {
        return REVISION;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("commodityId", getCommodityId())
                .append("quantity", getQuantity())
                .append("purchasePrice", getPurchasePrice())
                .append("retailPrice", getRetailPrice())
                .append("position", getPosition())
                .append("status", getStatus())
                .append("REVISION", getREVISION())
                .append("createdBy", getCreatedBy())
                .append("createdTime", getCreatedTime())
                .append("updatedBy", getUpdatedBy())
                .append("updatedTime", getUpdatedTime())
                .toString();
    }
}
