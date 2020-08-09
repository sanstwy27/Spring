package com.atsanstwy27.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /*
     * 1. 減餘額
     * 減去某個用戶的餘額
     */
    public void updateBalance(String userName,int price){
        String sql="UPDATE account SET balance=balance-? WHERE username=?";
        jdbcTemplate.update(sql, price,userName);


    }

    /*
     * 2. 按照某本書的IDBN獲取某本圖書的價格
     */
    public int  getPrice(String isbn){
        String sql="SELECT price FROM book WHERE isbn=?";
        return jdbcTemplate.queryForObject(sql,Integer.class,isbn);
    }

    /*
     * 3. 減庫存，減去某本書的庫存，為了簡單期間每次減一
     */

    public void updateStock(String isbn){
        String sql="UPDATE book_stock SET stock=stock-1 WHERE isbn=?";
        jdbcTemplate.update(sql, isbn);
    }

}
