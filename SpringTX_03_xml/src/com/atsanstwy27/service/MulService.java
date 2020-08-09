package com.atsanstwy27.service;

public class MulService {

    private BookService bookService;

    public void mulTx(){
        //是否回滾都是可以設置的
        //傳播行為來設置這個事務方法是不是和之前的大事務共享一個事務（使用同一條連接）
        //Propagation.REQUIRED 事務的屬性都是繼承於大事務
        //Propagation.REQUIRES_NEW
        bookService.checkOut("Tom", "ISBN-001");
        //Propagation.REQUIRED
        bookService.updatePrice("ISBN-002", 1001);

        //int i = 10 / 0;
    }

}
