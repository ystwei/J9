package com.weikun.service;

import com.weikun.dao.ArticleDAOImpl;
import com.weikun.dao.IArticleDAO;
import com.weikun.vo.Article;
import com.weikun.vo.PageBean;

/**
 * Created by Administrator on 2016/11/11.
 */
public class ArticleServiceImpl implements IArticleService {
    private IArticleDAO dao=new ArticleDAOImpl();
    public ArticleServiceImpl(){

    }
    @Override
    public PageBean queryArticleAll(int rootid, int curPage, int uid) {
        return dao.queryArticleAll(rootid,curPage,uid);
    }

    @Override
    public boolean delArticle(int id) {
        return dao.delArticle(id);
    }

    @Override
    public boolean addArticle(Article article) {
        return dao.addArticle(article);
    }

}
