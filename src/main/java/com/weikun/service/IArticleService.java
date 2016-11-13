package com.weikun.service;

import com.weikun.vo.Article;
import com.weikun.vo.PageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface IArticleService {
    public PageBean queryArticleAll(int rootid, int curPage, int uid);
    public boolean delArticle(int id) ;
    public boolean addArticle(Article article) ;
}
