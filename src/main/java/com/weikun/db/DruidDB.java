package com.weikun.db;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Administrator on 2016/11/6.
 */
public class DruidDB {
    private static DataSource ds;
    private static Connection conn;
    static{
        Properties pro=new Properties();
        try {
            pro.load(DruidDB.class.getClassLoader().getResourceAsStream("druid.ini"));

            ds= DruidDataSourceFactory.createDataSource(pro);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Test
    public void getConnection(){
        try {
            conn=ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
