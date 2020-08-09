package com.atsanstwy27.service;

import com.atsanstwy27.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;

public class BookService {

    @Autowired
    BookDao bookDao;

    public void checkOut(String userName,String isbn){
        //1. 減庫存
        bookDao.updateStock(isbn);
        //獲取價格
        int price=bookDao.getPrice(isbn);
        //2. 減餘額
        bookDao.updateBalance(userName, price);

        int i = 10 / 0;
    }

    public int getPrice(String isbn) {
        return bookDao.getPrice(isbn);
    }

    public void updatePrice(String isbn,int price){
        bookDao.updatePrice(isbn, price);

    }

    public void mulTx()
    {
        checkOut("Tom", "ISBN-001");
        updatePrice("ISBN-001", 998);
    }

}
