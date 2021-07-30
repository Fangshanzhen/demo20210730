package com.demo.postgresql.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DButils {

    /**静态方法，并抛出SQL异常	 */
    public static List converToList(ResultSet rs) throws SQLException {

        List list = new ArrayList();//new一个新的List
        ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();//将传进来的结果集用getMetaData方法用于获取数据集的数据（如列数）
        int columnCount = md.getColumnCount();//得到数据集的列数

        while (rs.next()) {//数据集不为空
            Map rowData = new HashMap();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }


    public static List converToList1(ResultSet rs) throws SQLException {

        List list = new ArrayList();//new一个新的List
        List list1 = new ArrayList();
        ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();//将传进来的结果集用getMetaData方法用于获取数据集的数据（如列数）
        int columnCount = md.getColumnCount();//得到数据集的列数

        while (rs.next()) {//数据集不为空
            for (int i = 1; i <= columnCount; i++) {
                list.add( rs.getObject(i));
            }
        }
        list1.addAll(list);
        return list1;
    }


    public static List converToList2(ResultSet rs) throws SQLException {

        List list = new ArrayList();//new一个新的List
        ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();//将传进来的结果集用getMetaData方法用于获取数据集的数据（如列数）
        int columnCount = md.getColumnCount();//得到数据集的列数

        while (rs.next()) {//数据集不为空

            for (int i = 1; i <= 1; i++) {
                list.add(md.getColumnName(i));
            }
        }
        return list;
    }
}
