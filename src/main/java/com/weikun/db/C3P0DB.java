package com.weikun.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import javax.activation.DataSource;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016/11/6.
 */
public class C3P0DB {
    private static ComboPooledDataSource ds;
    static{
        ds=new ComboPooledDataSource("mysql");

    }
    @Test
    public void getConnection(){
        try {
            ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
