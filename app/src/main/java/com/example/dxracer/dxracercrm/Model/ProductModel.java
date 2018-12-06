package com.example.dxracer.dxracercrm.Model;

public class ProductModel {

    private int id;
    private int productCatalogId;
    private String catalogName;
    private String createRealName;
    private String fcno;
    private String skuName;
    private int skuSalePrice;
    private int skuCost;
    private String saleStatus;
    private String skuMainImg;
    private String productDesc;
    private int  createUserId;
    private String createTime;
    private String fieldStr1;
    private boolean ischeck;


    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductCatalogId() {
        return productCatalogId;
    }

    public void setProductCatalogId(int productCatalogId) {
        this.productCatalogId = productCatalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCreateRealName() {
        return createRealName;
    }

    public void setCreateRealName(String createRealName) {
        this.createRealName = createRealName;
    }

    public String getFcno() {
        return fcno;
    }

    public void setFcno(String fcno) {
        this.fcno = fcno;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public int getSkuSalePrice() {
        return skuSalePrice;
    }

    public void setSkuSalePrice(int skuSalePrice) {
        this.skuSalePrice = skuSalePrice;
    }

    public int getSkuCost() {
        return skuCost;
    }

    public void setSkuCost(int skuCost) {
        this.skuCost = skuCost;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getSkuMainImg() {
        return skuMainImg;
    }

    public void setSkuMainImg(String skuMainImg) {
        this.skuMainImg = skuMainImg;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFieldStr1() {
        return fieldStr1;
    }

    public void setFieldStr1(String fieldStr1) {
        this.fieldStr1 = fieldStr1;
    }
}
