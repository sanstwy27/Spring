package com.atsanstwy27.service;

import com.atsanstwy27.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * id默認就是類名首字母小寫，也可以改：@Service("name")
 * 作用域默認就是單例的，也可以改：@Scope(value="prototype")變成多實例
 *
 * 屬性的自動注入
 * @Autowired：Spring會自動的為這個屬性賦值，且不用擔心空指針異常。
 * 				它一定會去容器中找到這個屬性的組件。
 * */
@Service
public class BookService {

    //自動裝配：自動的為這個屬性賦值
    @Autowired(required = false)
    private BookDao bookDao;

    public void save() {
        System.out.println("bookService>..正在調用dao幫你保存圖書...");
        bookDao.saveBook();
    }
}