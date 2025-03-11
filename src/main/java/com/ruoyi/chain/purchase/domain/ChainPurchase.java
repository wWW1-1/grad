package com.ruoyi.chain.purchase.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购对象 t_chain_purchase
 *
 * @author Shawn
 * @date 2024-02-26
 */
public class ChainPurchase extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 合作公司ID
     */
    @Excel(name = "合作公司商品ID")
    private Long cooCommodityId;

    private Long userId;

    /**
     * 采购人
     */
    private String userName;

    private Long cooperationId;

    private String cooperationName;

    private Long commodityId;
    private String commodityName;

    private String image;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Long quantity;

    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal amount;

    @Excel(name = "审核类型")
    private Long auditType;

    @Excel(name = "审核信息")
    private String auditInfo;

    /**
     * 运输方式
     */
    @Excel(name = "运输方式")
    private Long transportType;

    /**
     * 运送状态
     */
    @Excel(name = "运送状态")
    private Long transportStatus;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCooCommodityId() {
        return cooCommodityId;
    }

    public void setCooCommodityId(Long cooCommodityId) {
        this.cooCommodityId = cooCommodityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCooperationId() {
        return cooperationId;
    }

    public void setCooperationId(Long cooperationId) {
        this.cooperationId = cooperationId;
    }

    public String getCooperationName() {
        return cooperationName;
    }

    public void setCooperationName(String cooperationName) {
        this.cooperationName = cooperationName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public Long getAuditType() {
        return auditType;
    }

    public void setAuditType(Long auditType) {
        this.auditType = auditType;
    }

    public String getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(String auditInfo) {
        this.auditInfo = auditInfo;
    }

    public Long getTransportType() {
        return transportType;
    }

    public void setTransportType(Long transportType) {
        this.transportType = transportType;
    }

    public Long getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(Long transportStatus) {
        this.transportStatus = transportStatus;
    }

    public Long getREVISION() {
        return REVISION;
    }

    public void setREVISION(Long REVISION) {
        this.REVISION = REVISION;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("cooCommodityId", getCooCommodityId())
                .append("quantity", getQuantity())
                .append("amount", getAmount())
                .append("auditType", getAuditType())
                .append("auditInfo", getAuditInfo())
                .append("transportType", getTransportType())
                .append("transportStatus", getTransportStatus())
                .append("REVISION", getREVISION())
                .append("createdBy", getCreatedBy())
                .append("createdTime", getCreatedTime())
                .append("updatedBy", getUpdatedBy())
                .append("updatedTime", getUpdatedTime())
                .toString();
    }
}
