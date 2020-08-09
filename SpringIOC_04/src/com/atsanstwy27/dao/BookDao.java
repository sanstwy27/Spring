package com.atsanstwy27.dao;

import org.springframework.stereotype.Repository;

/**
 * id默認就是類名首字母小寫，也可以改：@Repository("name")
 * 作用域默認就是單例的，也可以改：@Scope(value="prototype")變成多實例
 *
 * 屬性的自動注入
 * @Autowired：Spring會自動的為這個屬性賦值，且不用擔心空指針異常。
 * 				它一定會去容器中找到這個屬性的組件。
 * */
@Repository
public class BookDao {

    public void saveBook() {
        System.out.println("圖書已經保存...");
    }
}