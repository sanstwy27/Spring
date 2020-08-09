package com.practice;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {

    //方式一：
    @Test
    public void testConnection1() throws SQLException {

        //獲取Driver的實現類對像
        Driver driver = new com.mysql.cj.jdbc.Driver();

        String url = "XXXX/student";
        Properties info = new Properties();
        info.setProperty("user", "XXXX");
        info.setProperty("password", "XXXX");

        Connection conn = driver.connect(url, info);

        System.out.println(conn);
    }

    //方式二：對方式一的迭代：在如下的程序中不出現第三方的api，使得程序具有更好的可移植性
    @Test
    public void testConnection2() throws Exception {
        //1、獲取Driver實現類對像：使用反射
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //2、提供要連接的數據庫
        String url = "***************/student";

        //3、提供連接需要的用戶名和密碼
        Properties info = new Properties();
        info.setProperty("user", "XXXXXX");
        info.setProperty("password", "XXXXXX");

        //4、獲取連接
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }

    //方式三：使用DriverManager替換Driver
    @Test
    public void testConnection3() throws Exception {
        //1、獲取Driver的實現類對像
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //2、提供另外三個連接的基本信息：
        String url = "***************/student";
        String user = "XXXXXX";
        String password = "XXXXXX";

        //註冊驅動
        DriverManager.registerDriver(driver);

        //獲取連接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    //方式四：優化方式三。可以只是加載驅動，而不用顯示的註冊驅動了。
    @Test
    public void testConnection4() throws Exception {

        //1、提供另外三個連接的基本信息：
        String url = "***************/student";
        String user = "XXXXXX";
        String password = "XXXXXX";

        //2、加載驅動
        Class.forName("com.mysql.cj.jdbc.Driver");

        //3、獲取連接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    //方式五：將數據庫連接需要的基本信息聲明在配置文件中

    /**
     * 好處：實現了數據與代碼的分離，實現瞭解耦；
     * 如果需要修改配置文件信息，可以避免程序重新打包。
     */
    @Test
    public void testConnection5() throws Exception {
        //1、讀取配置文件中的基本信息
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("conf/dbconfig.properties");
        Properties pros = new Properties();
        pros.load(is);
        String user = pros.getProperty("user");
        String url = pros.getProperty("url");
        String password = pros.getProperty("password");
        //2、加載驅動
        Class.forName("com.mysql.cj.jdbc.Driver");
        //3、獲取鏈接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    //在java.sql包中有3個接口分別定義了對數據庫的調用的不同方式：
    /**
     * Statement：用於執行靜態SQL語句並返回它所生成結果的對象。（不常用了）
     * PreparedStatement：SQL語句被預編譯並存儲在此對像中，可以使用此對像多次
     * 					  高效地執行該語句。
     * CallableStatement：用於執行SQL存儲過程。
     *
     * Statement弊端：需要拼寫sql語句，並且存在SQL注入的問題。
     * sql注入：用戶名和密碼輸入錯誤也可登陸成功
     * sql注入是利用某些系統沒有對用戶輸入的數據進行充分的檢查，而在用戶
     * 輸入數據中注入非法的SQL語句段或命令，從而利用系統的SQL引擎完成惡意
     * 行為的做法。
     * 解決方案：用PreparedStatement取代Statement
     * */
    //（具體使用之後再說）

}
