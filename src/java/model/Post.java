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
    private String briefInfo;
    private String thumbnail;
    private int count;
    private int authorID;
    private int serviceID;
    private Date createdDate;
    private String categoryPost;
    private boolean statusPost;

    public Post() {
    }

    public Post(int postID, String title, String content, String briefInfo, String thumbnail, int count, int authorID, int serviceID, Date createdDate, String categoryPost, boolean statusPost) {
        this.postID = postID;
        this.title = title;
        this.content = content;
        this.briefInfo = briefInfo;
        this.thumbnail = thumbnail;
        this.count = count;
        this.authorID = authorID;
        this.serviceID = serviceID;
        this.createdDate = createdDate;
        this.categoryPost = categoryPost;
        this.statusPost = statusPost;
    }

    public Post(String title, String content, String briefInfo, String thumbnail, int serviceID, Date createdDate, String categoryPost, boolean statusPost) {
        this.title = title;
        this.content = content;
        this.briefInfo = briefInfo;
        this.thumbnail = thumbnail;
        this.serviceID = serviceID;
        this.createdDate = createdDate;
        this.categoryPost = categoryPost;
        this.statusPost = statusPost;
    }

    public int getPostID() {
        return postID;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getBriefInfo() {
        return briefInfo;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getAuthorID() {
        return authorID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getCategoryPost() {
        return categoryPost;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setCategoryPost(String categoryPost) {
        this.categoryPost = categoryPost;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isStatusPost() {
        return statusPost;
    }

    public void setStatusPost(boolean statusPost) {
        this.statusPost = statusPost;
    }

}
