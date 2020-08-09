package com.atsanstwy27.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MulService {

    @Autowired
    private BookService bookService;

    /**
     * multx() {
     *     //REQUIRED
     *     A(){
     *         //REQUIRED_NEW
     *         B(){}
     *         //REQUIRED
     *         C(){}
     *     }
     *
     *     //REQUIRED_NEW
     *     D(){
     *         DDDD() //REQUIRED_NEW不崩, REQUIRED崩
     *         //REQUIRED
     *         E(){
     *             //REQUIRED_NEW
     *             F(){
     *                 // 10/0 (E崩,G崩,A,C崩)
     *             }
     *         }
     *     }
     * }
     *
     *  10/0(B成功,D整個分支下全部成功
     *  任何處崩, 已經執行的REQUIRES_NEW都會成功
     *
     *  如果是REQUIRED, 事務的屬性都是繼承於大事務的,ex: 子事務的timeout無效
     *  而propagattion=Propagation.REQUIRES_NEW都可調整
     *  默認, REQUIRED,
     *
     *  REQUIRED, 將之前事務用的connection傳遞給這個方法使用,
     *  REQUIRED_NEW, 這個方法直接使用新的connection
     *
     */
    @Transactional
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
