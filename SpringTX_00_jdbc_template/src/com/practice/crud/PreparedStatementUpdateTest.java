package com.practice.crud;

import com.practice.ConnectionTest;
import com.practice.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * 使用PreparedStatement來替換Statement，實現對數據表的增刪改查操作。
 * <p>
 * insert/delete from/update/select
 */
public class PreparedStatementUpdateTest {

    // 向student表中添加一條記錄（表名因人而異）
    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 1、讀取配置文件中的基本信息
            InputStream is = ConnectionTest.class.getClassLoader()
                    .getResourceAsStream("conf/dbconfig.properties");
            Properties pros = new Properties();
            pros.load(is);
            String user = pros.getProperty("user");
            String url = pros.getProperty("url");
            String password = pros.getProperty("password");
            // 2、加載驅動
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 3、獲取鏈接
            conn = DriverManager.getConnection(url, user, password);
            // 4、預編譯sql語句，返回PreparedStatement的實例
            // ?：佔位符
            String sql = "insert into student(stuname,stuscore,stunum,stubirth)values(?,?,?,?)";
            ps = conn.prepareStatement(sql);
            // 5、填充佔位符
            ps.setString(1, "蔡徐坤");
            ps.setDouble(2, 80);
            ps.setInt(3, 1007);
            // 設置日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("2000-11-11");
            ps.setDate(4, new Date(date.getTime()));

            // 6、執行操作
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7、資源關閉
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
    }

    // 修改student表中的一條記錄
    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 1、獲取數據庫的連接
            conn = JDBCUtils.getConnection();
            // 2、預編譯sql語句，返回PreparedStatement的實例
            String sql = "update student set stuname = ? where stuid = ?";
            ps = conn.prepareStatement(sql);
            // 3、填充佔位符
            ps.setObject(1, "蔡徐坤坤");
            ps.setObject(2, 7);
            // 4、執行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5、資源關閉
            JDBCUtils.closeResource(conn, ps);
        }
    }

    // 通用的增刪改操作
    // sql中佔位符的個數與可變形參的個數相同
    public void update(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 1、獲取數據庫連接
            conn = JDBCUtils.getConnection();
            // 2、預編譯sql語句，返回PreparedStatement實例
            ps = conn.prepareStatement(sql);
            // 3、填充佔位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]); // 小心參數聲明錯誤
            }
            // 4、執行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5、關閉資源
            JDBCUtils.closeResource(conn, ps);
        }
    }

    @Test
    public void testCommonUpdate() {
        String sql = "delete from student where stuid = ?";
        update(sql, 7);
    }

}
