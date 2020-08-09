package com.practice.crud;

import com.practice.bean.Student;
import com.practice.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 針對student表的查詢
 */
public class StudentForQuery {

    @Test
    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select stuname,stuscore,stunum,stubirth from student where stuid = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 6);
            // 執行並返回結果集
            resultSet = ps.executeQuery();
            // 處理結果集
            if (resultSet.next()) { // 判斷結果集的下一條是否有數據，如果返回true，指針下移；否則指針不下移
                // 獲取當前數據的各個字段值
                String stuname = resultSet.getString(1);
                double stuscore = resultSet.getDouble(2);
                int stunum = resultSet.getInt(3);
                Date stubirth = resultSet.getDate(4);

                // 方式一
                //System.out.println("姓名：" + stuname + "分數：" + stuscore + "學號："
                //		+ stunum + "生日：" + stubirth);

                // 方式二
                //Object[] data = new Object[] { stuname, stuscore, stunum, stubirth };

                // 方式三 ：將數據封裝成一個對像（推薦）
                Student student = new Student(stuname, stuscore, stunum, stubirth);
                System.out.println(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //關閉資源
        JDBCUtils.closeResource(conn, ps, resultSet);
    }
}