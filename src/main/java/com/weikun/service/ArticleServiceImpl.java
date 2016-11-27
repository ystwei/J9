package com.weikun.service;

import com.alibaba.fastjson.JSON;
import com.weikun.dao.ArticleDAOImpl;
import com.weikun.dao.IArticleDAO;
import com.weikun.vo.Article;
import com.weikun.vo.PageBean;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public boolean delReply(int id) {
        return dao.delReply(id);
    }

    @Override
    public boolean addArticle(Article article) {
        return dao.addArticle(article);
    }


    @Override
    public String queryArticleById(int id) {
        List<Article> list=dao.queryArticleById(id);

        Map<String,Object> map=new HashMap<String,Object>();
        if(list!=null){
            map.put("title",list.get(0).getTitle());//主贴title，为了配合前端的那个窗体标题

            map.put("list",list.subList(1,list.size()));
        }



        System.out.println(JSON.toJSONString(map,true));

        return JSON.toJSONString(map,true);
    }

}
