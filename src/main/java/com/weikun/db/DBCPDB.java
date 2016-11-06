package com.weikun.db;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Administrator on 2016/11/6.
 */
public class DBCPDB {
    private static DataSource ds;
    static {

        try {
            Properties pro=new Properties();
            pro.load(DBCPDB.class.getClassLoader().getResourceAsStream("dbcp.ini"));
            ds=BasicDataSourceFactory.createDataSource(pro);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.print(getConnection());
    }
    public static Connection getConnection(){
        try {
           return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}
