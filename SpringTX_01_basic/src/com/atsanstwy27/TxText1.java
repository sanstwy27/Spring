package com.atsanstwy27;

import com.atsanstwy27.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class TxText1 {

    @Autowired
    ApplicationContext ioc = new ClassPathXmlApplicationContext("conf/tx.xml");

    @Test
    public void test01() throws SQLException {
        BookService bookService=ioc.getBean(BookService.class);
        bookService.checkOut("Tom", "ISBN-001");
        System.out.println("checkOut.");
    }

}
