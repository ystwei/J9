package com.weikun.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/6.
 */
public class BBSUser implements Serializable {
    private int id;
    private String username;
    private String password;

    public List<Article> getAlist() {
        return alist;
    }

    public void setAlist(List<Article> alist) {
        this.alist = alist;
    }

    private List<Article> alist;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    private String picPath;//头像上传路径


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
