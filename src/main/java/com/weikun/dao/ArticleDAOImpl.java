package com.weikun.dao;

import com.weikun.db.DBCPDB;
import com.weikun.vo.Article;
import com.weikun.vo.BBSUser;
import com.weikun.vo.PageBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
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
                    //a.setContent(rs.getString("content"));

                    //读clob
                    BufferedReader br=new BufferedReader(rs.getCharacterStream("content"));
                    StringBuffer sb=new StringBuffer();
                    String n="";
                    try {
                        while((n=br.readLine())!=null){

                            sb.append(n);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally{
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    a.setContent(sb.toString());
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

    @Override
    public boolean delArticle(int id) {
        boolean flag=false;
        PreparedStatement pstmt=null;
        String sql="delete from article where id =? or rootid=?";
        try {
            pstmt=conn.prepareStatement(sql);

            pstmt.setInt(1,id);
            pstmt.setInt(2,id);

            flag=pstmt.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return flag;
    }

    @Override
    public boolean addArticle(Article article) {
        PreparedStatement pstmt=null;
        boolean flag=false;
        try {
            pstmt=conn.prepareStatement("insert into " +
                    "article(rootid,title,content,userid,datetime) " +
                    "values(?,?,?,?,now())");

            pstmt.setInt(1,article.getRootid());
            pstmt.setString(2,article.getTitle());
            //写clob
            StringReader sr=new StringReader(article.getContent());
            pstmt.setCharacterStream(3,sr,article.getContent().length());
            pstmt.setInt(4,article.getUser().getId());
            flag=pstmt.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     *
     * @param id:你要查询的主贴id
     * @return返回所有从贴的列表
     */
    @Override
    public List<Article> queryArticleById(int id) {
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List<Article> list=new ArrayList<>();
        try {

            String sql="select *  from article \n" +
                    "where rootid=? or id=? order by id ";
            pstmt=conn.prepareStatement(sql);

            pstmt.setInt(1,id);
            pstmt.setInt(2,id);

            rs=pstmt.executeQuery();

            while(rs.next()){

                Article a=new Article();
                a.setId(rs.getInt("id"));
                a.setTitle(rs.getString("title"));
                a.setRootid(rs.getInt("rootid"));
                BBSUser usr=new BBSUser();
                usr.setId(rs.getInt("userid"));

                a.setUser(usr);

                //读clob
                BufferedReader br=new BufferedReader(rs.getCharacterStream("content"));
                StringBuffer sb=new StringBuffer();
                String n="";
                try {
                    while((n=br.readLine())!=null){

                        sb.append(n);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                a.setContent(sb.toString());

                list.add(a);

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }


        return list;
    }
}
