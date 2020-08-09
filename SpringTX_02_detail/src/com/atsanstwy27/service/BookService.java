package com.atsanstwy27.service;

import com.atsanstwy27.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;
    /*
     * 事務細節:
     * isolation-Isolation, 事務的隔離級別
     * propagation-Propagation, 事務的傳播行為
     *
     * 異常分類,
     *       運行時異常(非檢查異常), 可以不用處理, 默認都回滾,
     *       編譯時異常(檢查異常), try-catch或是throws, 默認不回滾
     * 事務的回滾,
     * noRollbackFor-Class[], 那些異常事務可以不回滾; (可以讓默認回滾的異常給他不回滾)
     * noRollbackForClassName-String[] (String全類名)
     * rollbackFor-Class[], 那些異常事務需要回滾; (原本不回滾的異常指定讓其回滾)
     * rollbackForClassName-String[]
     *
     *
     * readOnly-boolean: 設置事務為只讀事務, 可加快查詢
     * timeout-int: 超時, 事務超出指定執行時常後自動終止並回滾
     *
     */
    //@Transactional(timeout = 3, readOnly = false, noRollbackFor = {ArithmeticException.class})
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void checkOut(String userName,String isbn){
        //1. 減庫存
        bookDao.updateStock(isbn);

        /*
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         */
        //獲取價格
        int price=bookDao.getPrice(isbn);
        //2. 減餘額
        bookDao.updateBalance(userName, price);

        //int i = 10 / 0;
        //new File("D://hahahaha.haha");
    }

    /**
     * Dirty Read Test
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
    public int getPrice(String isbn) {
        return bookDao.getPrice(isbn);
    }


    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void updatePrice(String isbn,int price){
        bookDao.updatePrice(isbn, price);

    }

    @Transactional
    public void mulTx()
    {
        checkOut("Tom", "ISBN-001");
        updatePrice("ISBN-001", 998);
        int i= 10 / 0;
    }

}
