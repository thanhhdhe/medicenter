/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class Service {

    private int serviceID;
    private String title;
    private String brief;
    private String thumbnail;
    private int categoryID;
    private double originalPrice;
    private double salePrice;
    private String serviceDetail;
    private Date updateDate;
    private Boolean status;

    public Service() {
    }

    public Service(int serviceID, String title, String brief, String thumbnail, int categoryID, double originalPrice, double salePrice, String serviceDetail, Date updateDate) {
        this.serviceID = serviceID;
        this.title = title;
        this.brief = brief;
        this.thumbnail = thumbnail;
        this.categoryID = categoryID;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.serviceDetail = serviceDetail;
        this.updateDate = updateDate;
    }

    public Service(int serviceID, String title, String brief, String thumbnail, int categoryID, double originalPrice, double salePrice, String serviceDetail, Date updateDate, Boolean status) {
        this.serviceID = serviceID;
        this.title = title;
        this.brief = brief;
        this.thumbnail = thumbnail;
        this.categoryID = categoryID;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.serviceDetail = serviceDetail;
        this.updateDate = updateDate;
        this.status = status;
    }

    public Service(String title, String brief, String thumbnail, int categoryID, double originalPrice, double salePrice, String serviceDetail, Date updateDate, Boolean status) {
        this.title = title;
        this.brief = brief;
        this.thumbnail = thumbnail;
        this.categoryID = categoryID;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.serviceDetail = serviceDetail;
        this.updateDate = updateDate;
        this.status = status;
    }

    public Service(int ServiceID, String title) {
        this.serviceID = ServiceID;
        this.title = title;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int ServiceID) {
        this.serviceID = ServiceID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
