package com.atsanstwy27;

import com.atsanstwy27.service.BookService;
import com.atsanstwy27.service.MulService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class TxText3 {

    @Autowired
    ApplicationContext ioc = new ClassPathXmlApplicationContext("conf/tx.xml");

    @Test
    public void test02() throws SQLException {
        BookService bookService=ioc.getBean(BookService.class);
        bookService.checkOut("Tom", "ISBN-001");
        System.out.println("checkOut..");
    }

    /**
     * 有事務的業務邏輯, 容器中保存的是這個業務邏輯的代理對像
     * @throws SQLException
     */
    @Test
    public void test03() throws SQLException {
        BookService bookService=ioc.getBean(BookService.class);
        int price = bookService.getPrice("ISBN-001");
        System.out.println("讀取到的數據: " + price);
        System.out.println(bookService.getClass());
    }

    @Test
    public void test04() {
        BookService bookService=ioc.getBean(BookService.class);

        MulService bean = ioc.getBean(MulService.class);
        bean.mulTx();
    }

    @Test
    public void test05() throws FileNotFoundException {
        BookService bookService = ioc.getBean(BookService.class);
        bookService.mulTx();
        //如果MulService-mulTx()--調用BookService兩個方法
        //BookService--mulTX()--直接調用兩個方法
        /*
         * MulServiceProxy.mulTx() {
         *    bookServiceProxy.checkout();
         *    bookServiceProxy.updatePrice();
         * }
         *
         * 本類方法的嵌套調用就是一個事務
         * BookServiceProxy.mulTx(){
         *     checkout();
         *     updatePrice();
         *     //相當於把這兩個方法中的執行語句複製過來
         *     //	bookDao.updateStock(isbn);
         *     // int price=bookDao.getPrice(isbn);
         *     // bookDao.updateBalance(userName, price);
         *     // bookDao.updatePrice(isbn, price);
         * }
         */
    }

}
