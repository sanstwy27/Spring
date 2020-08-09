package com.atsanstwy27.service;

import com.atsanstwy27.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;

public class BookServiceExt extends BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public void save() {
        System.out.println("bookServiceExt>..正在調用dao幫你保存圖書...");
        bookDao.saveBook();
    }
}