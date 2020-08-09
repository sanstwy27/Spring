package com.atsanstwy27.servlet;

import com.atsanstwy27.dao.BookDao;
import com.atsanstwy27.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

/**
 * id默認就是類名首字母小寫，也可以改：@Controller("name")
 * 作用域默認就是單例的，也可以改：@Scope(value="prototype")變成多實例
 *
 * 屬性的自動注入
 * @Autowired：Spring會自動的為這個屬性賦值，且不用擔心空指針異常。
 * 				它一定會去容器中找到這個屬性的組件。
 * */
@Controller
public class BookServlet {

    //自動裝配：自動的為這個屬性賦值
    //@Qualifier:指定一個名作為id，讓spring別使用變量名作為id
    @Qualifier("bookService")
    @Autowired(required = false)
    private BookService bookServiceExt2;

    public void doGet() {
        bookServiceExt2.save();
    }

    /**
     * 如果方法上有@Autowired的話，
     * 1)這個方法也會在bean創建的時候自動運行
     * 2)這個方法上的每一個參數都會自動注入值
     * */
    @Autowired(required = false)
    public void hahaha(BookDao bookDao, @Qualifier("bookService") BookService bookService) {
        System.out.println("Spring運行了這個方法..." + bookDao + "=>" + bookService);
    }
}