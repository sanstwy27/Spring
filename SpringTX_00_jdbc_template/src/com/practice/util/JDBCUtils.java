package com.practice.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 操作數據庫的工具類
 */
public class JDBCUtils {

    /**
     * 獲取數據庫的連接
     */
    public static Connection getConnection() throws Exception {
        // 1、讀取配置文件中的基本信息
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("conf/dbconfig.properties");
        Properties pros = new Properties();
        pros.load(is);
        String user = pros.getProperty("user");
        String url = pros.getProperty("url");
        String password = pros.getProperty("password");
        // 2、加載驅動
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 3、獲取鏈接
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    //關閉資源的操作
    public static void closeResource(Connection conn, Statement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 關閉資源的操作
    public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
