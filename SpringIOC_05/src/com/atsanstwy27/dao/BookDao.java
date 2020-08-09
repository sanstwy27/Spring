package com.atsanstwy27.dao;


import com.atsanstwy27.bean.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao extends BaseDao<Book> {

    @Override
    public void save() {
        // TODO Auto-generated method stub
        System.out.println("BookDao...保存圖書...");
    }

}