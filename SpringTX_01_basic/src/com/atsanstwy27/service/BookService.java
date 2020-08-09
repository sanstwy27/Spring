package com.atsanstwy27.service;

import com.atsanstwy27.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;
    /*
     * 結賬：傳入哪個用戶買了哪本書
     */
    @Transactional
    public void checkOut(String userName,String isbn){
        //1. 減庫存
        bookDao.updateStock(isbn);
        //獲取價格
        int price=bookDao.getPrice(isbn);
        //2. 減餘額
        bookDao.updateBalance(userName, price);

        //int i = 10 / 0;
    }

}
