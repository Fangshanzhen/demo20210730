package com.demo.postgresql.constant;

public class Constant {
    public final static String URL = "jdbc:postgresql://localhost:5432/postgres";
    public final static String USER = "postgres";
    public final static String PASSWORD = "123456";
    public final static String DRIVER = "org.postgresql.Driver";
    public final static String SCHEMA_SQL = "select schemaname, tablename from pg_tables where schemaname = #";
    public final static String COLUMN_SQL = "select column_name from information_schema.columns where table_schema= # and table_name= &;";
    public final static String FIND_SQL = "select * from $ where @ like # limit 1";
    public final static String FIND_ALL_SCHEMA = "select nspname from pg_catalog.pg_namespace;";
}
