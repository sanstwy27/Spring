package com.atsanstwy27;

import com.atsanstwy27.service.BookService;
import com.atsanstwy27.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOC5Test {

    ApplicationContext ioc = new ClassPathXmlApplicationContext("conf/ioc5.xml");

    @Test
    public void test() {
        BookService bookService = ioc.getBean(BookService.class);
        UserService userService = ioc.getBean(UserService.class);

        bookService.save();
        userService.save();

        //獲取父類：
        System.out.println(bookService.getClass().getSuperclass());
        //獲取泛型父類：
        System.out.println(bookService.getClass().getGenericSuperclass());

        //Spring中可以用帶泛型的父類類型來確定這個子類的類型

    }

}
