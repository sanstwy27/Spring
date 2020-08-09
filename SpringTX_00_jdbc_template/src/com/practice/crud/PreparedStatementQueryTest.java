package com.practice.crud;

import com.practice.bean.Student;
import com.practice.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

//使用PreparedStatement實現對於不同表的通用的查詢操作
public class PreparedStatementQueryTest {

    @Test
    public void testGetInstance() {
        String sql = "select stuname,stuscore,stunum,stubirth from student where stuid = ?";
        Student st = getInstance(Student.class, sql, 6);
        System.out.println(st);
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
                    Field field = Student.class.getDeclaredField(columnName);
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

    @Test
    public void testGetForList() {
        String sql = "select stuname,stuscore,stunum,stubirth from student where stuid < ?";
        List<Student> list = getForList(Student.class, sql, 3);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            System.out.println(list.get(i));
        }
    }

    // 針對於不同的表的通用的查詢操作，返回表中的多條記錄
    public <T> List<T> getForList(Class<T> clazz, String sql, Object... args) {
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
            // 創建集合對像
            ArrayList<T> list = new ArrayList<T>();
            while (rs.next()) {
                T t = clazz.newInstance();
                // 處理結果集一行數據中的每一個列：給t對像指定的屬性賦值
                for (int i = 0; i < columnCount; i++) {
                    // 獲取列值
                    Object columnValue = rs.getObject(i + 1);

                    // 獲取每個列的列名
                    String columnName = rsmd.getColumnName(i + 1);

                    // 給st對像指定的columnName屬性，賦值為columnValue，反射完成
                    Field field = Student.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }

}