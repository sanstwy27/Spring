package com.practice.crud;

import com.practice.bean.User;
import com.practice.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

/**
 * PreparedStatement除了解決Statement的拼串、sql問題之外，PreparedStatement還有哪些好處？
 * 1、PreparedStatement操作Blob的數據，而Statement做不到
 * 2、PreparedStatement可以實現更高效的批量操作
 * */
public class PreparedStatementTest {

    @Test
    public void testLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("請輸入用戶名：");
        //String user = scanner.nextLine();
        String user = "abc";
        System.out.println("請輸入密碼：");
        //String password = scanner.nextLine();
        String password = "XXXX";
        String sql = "SELECT user,password FROM user_table WHERE user = ? and password = ?";
        User returnUser = getInstance(User.class, sql, user, password);
        if (returnUser != null) {
            System.out.println("登陸成功");
        } else {
            System.out.println("用戶名不存在或密碼錯誤");
        }
    }

    // 針對於不同的表的通用的查詢操作，返回表中的一條記錄
    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            // 獲取結果集的元數據
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通過ResultSetMetaData獲取結果集中的列數
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                T t = clazz.newInstance();
                // 處理結果集一行數據中的每一個列
                for (int i = 0; i < columnCount; i++) {
                    // 獲取列值
                    Object columnValue = rs.getObject(i + 1);

                    // 獲取每個列的列名
                    String columnName = rsmd.getColumnName(i + 1);

                    // 給st對像指定的columnName屬性，賦值為columnValue，反射完成
                    Field field = User.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }
}