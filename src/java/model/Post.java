/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author quanh
 */
public class Post {

    private int postID;
    private String title;
    private String content;
    private int authorID;
    private int serviceID;
    private Date createdDate;
    private int categoryPostID;
    private String briefInfo;
    private String thumbnail;

    public Post() {
    }

    public Post(int postID, String title, String content, int authorID, int serviceID, Date createdDate, int categoryPostID, String briefInfo, String thumbnail) {
        this.postID = postID;
        this.title = title;
        this.content = content;
        this.authorID = authorID;
        this.serviceID = serviceID;
        this.createdDate = createdDate;
        this.categoryPostID = categoryPostID;
        this.briefInfo = briefInfo;
        this.thumbnail = thumbnail;
    }

    public Post(String title, String content, int authorID, int serviceID, Date createdDate, int categoryPostID, String briefInfo, String thumbnail) {
        this.title = title;
        this.content = content;
        this.authorID = authorID;
        this.serviceID = serviceID;
        this.createdDate = createdDate;
        this.categoryPostID = categoryPostID;
        this.briefInfo = briefInfo;
        this.thumbnail = thumbnail;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getCategoryPostID() {
        return categoryPostID;
    }

    public void setCategoryPostID(int categoryPostID) {
        this.categoryPostID = categoryPostID;
    }

    public String getBriefInfo() {
        return briefInfo;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
