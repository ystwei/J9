package com.weikun.dao;

import com.weikun.db.DruidDB;
import com.weikun.vo.BBSUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016/11/6.
 */
public class UserDAOImpl implements  IUserDAO{
    private Connection conn= DruidDB.getConnection();
    public boolean login(BBSUser user) {
        String sql="select * from bbsuser where username=? and password=?";
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        boolean flag=false;
        try {
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,user.getUsername());
            pstmt.setString(2,user.getPassword());

            rs=pstmt.executeQuery();
            flag=rs.next();


        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        return flag;
    }
}
