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
//    PostID INT  IDENTITY(1,1) PRIMARY KEY,
    private int identity;
//    Title VARCHAR(100) NOT NULL,
    private String title;
//    Content TEXT NOT NULL,
    private String text;
//    AuthorID INT NOT NULL,
    private int authorID;
//	ServiceID INT NOT NULL,
    private int serviceID;
//    CreatedDate DATETIME NOT NULL,
    private Date createdDate;
//    CategoryPostID INT NOT NULL,
    private int categoryPostID;

    public Post() {
    }

    public Post(int identity, String title, String text, int authorID, int serviceID, Date createdDate, int categoryPostID) {
        this.identity = identity;
        this.title = title;
        this.text = text;
        this.authorID = authorID;
        this.serviceID = serviceID;
        this.createdDate = createdDate;
        this.categoryPostID = categoryPostID;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
    
}
