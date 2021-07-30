//package com.demo.postgresql;
//
//import com.demo.postgresql.utils.DbUtils;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class test {
//    @SuppressWarnings("unchecked")
//    public static void main(String[] args) {
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            Class.forName("org.postgresql.Driver");
//            c = DriverManager
//                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
//                            "postgres", "123456");
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//
//            String schemaName = "'public'";
//            List<String> tableList = new ArrayList<>();
//
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery("select schemaname, tablename from pg_tables where schemaname = #".replace("#", schemaName));
//            String schemaname = null;
//            while (rs.next()) {
//                schemaname = rs.getString("schemaname");
//                String tablename = rs.getString("tablename");
//                tableList.add(tablename);
//
//            }
//            String findString = "宋广军";
//
//            for (String s : tableList) {
//                String sql = "select column_name from information_schema.columns where table_schema=# and table_name=&;".replace("#", "'" + schemaname + "'").replace("&", "'" + s + "'"); // \"
//                ResultSet dataResult = stmt.executeQuery(sql);
//                List<Object> dataList = new ArrayList<Object>();
//                dataList = DbUtils.converToList(dataResult);
//                for (Object o : dataList) {
//                    String findSql = "select * from $ where @ like # limit 1".replace("$", "\"" + schemaname + "\"" + "." + "\"" + s + "\"")
//                            .replace("@", String.valueOf(o).replace("{column_name=", "").replace("}", "")).replace("#", "'%" + findString + "%'");
//
//                    ResultSet data = null;
//                    try {
//                        data = stmt.executeQuery(findSql);
//                    } catch (Exception e) {
//                        c.rollback();
//                        continue;
//                    }
//                    if (DbUtils.converToList(data).size() > 0) {
//                        System.out.println(schemaname + "  " + s + "  " + String.valueOf(o).replace("{column_name=", "").replace("}", ""));
//                    }
//                }
//
//            }
//            rs.close();
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//        System.out.println("Operation done successfully");
//    }
//}