package com.qhit.test;

import com.qhit.utils.POIExcel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by tp on 2018/5/10
 */
public class poiexcal {
    public static void main(String[] args) throws Exception {
        String title = "商品代码表";
        String[] name = {"序号","devname","typeid","devdate","devuser","compid"};
        //获取数据
        Connection connection = POIExcel.getConnection();
        Statement statement = connection.createStatement();
        String sql = "  SELECT * FROM base_device bd";
        ResultSet rs = statement.executeQuery(sql);

        POIExcel.exportExcel(title,name,rs);
    }
}
