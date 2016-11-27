package com.weikun.dao;

import com.weikun.vo.Article;
import com.weikun.vo.PageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface IArticleDAO {

    PageBean queryArticleAll(int rootid, int curPage, int usrid);//查询所有主贴，且rootid=0
    boolean delArticle(int id);
    boolean addArticle(Article article);
    List<Article> queryArticleById(int id);
}
