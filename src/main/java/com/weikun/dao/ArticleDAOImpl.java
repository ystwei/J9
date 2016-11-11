package com.weikun.dao;

import com.weikun.db.DBCPDB;
import com.weikun.vo.Article;
import com.weikun.vo.BBSUser;
import com.weikun.vo.PageBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class ArticleDAOImpl implements IArticleDAO {
    private Connection conn;
    public ArticleDAOImpl(){

        conn= DBCPDB.getConnection();
    }

    /**
     *
     * @param rootid
     * @param curPage
     * @param uid
     * @return
     */
    @Override
    public PageBean queryArticleAll(int rootid, int curPage, int uid) {
        CallableStatement cs=null;
        PageBean pb=new PageBean();
        ResultSet rs=null;
        String sql="call mypage(?,?,?,?,?)";
        List<Article> list=new ArrayList<>();
        try {
            pb.setCurPage(curPage);
            cs=conn.prepareCall(sql);
            cs.setInt(1,curPage);
            cs.setInt(2,uid);
            cs.registerOutParameter(3,Types.INTEGER);
            cs.registerOutParameter(4,Types.INTEGER);
            cs.registerOutParameter(5,Types.INTEGER);

            boolean has=cs.execute();
            while(has){
                rs=cs.getResultSet();

                pb.setMaxPage(cs.getInt(3));//取返回值，
                pb.setMaxRowCount(cs.getInt(4));
                pb.setRowsPerPage(cs.getInt(5));

                while(rs.next()){
                    Article a=new Article();
                    a.setId(rs.getInt("id"));

                    a.setRootid(rs.getInt("rootid"));
                    a.setTitle(rs.getString("title"));
                    a.setContent(rs.getString("content"));
                    a.setDatetime(rs.getString("datetime"));
                    BBSUser user=new BBSUser();
                    user.setId(rs.getInt("userid"));
                    a.setUser(user);
                    list.add(a);
                }


                has=cs.getMoreResults();

            }

            pb.setData(list);



        } catch (SQLException e) {
            e.printStackTrace();
        }finally{

            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(cs!=null){
                try {
                    cs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return pb;
    }
}
