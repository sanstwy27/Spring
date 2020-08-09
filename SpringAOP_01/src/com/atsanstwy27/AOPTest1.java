package com.atsanstwy27;

import com.atsanstwy27.impl.MyMathCalculator;
import com.atsanstwy27.inter.Calculator;
import com.atsanstwy27.proxy.CalculatorProxy;
import org.junit.jupiter.api.Test;

public class AOPTest1 {

    /**
     * AOP:(Aspect Oriented Programming)面向切面編程；
     OOP:(Object Oriented Programming)面向對像編程；

     AOP：基於OOP基礎之上新的編程思想；
     面向切面編程是指：在程序運行期間，將某段代碼動態的切入到指定方法的指定位置
     進行運行的這種編程方式。

     場景：計算器運行計算方法的時候，進行日誌記錄；
     加日誌記錄：
     1）直接編寫在方法內部；（不推薦。修改維護麻煩）

     日誌記錄：系統的輔助功能；
     業務邏輯：（核心功能）
     耦合

     2）我們希望的是：
     業務邏輯和日誌記錄模塊，能在核心功能運行期間，
     自己動態的加上；
     *
     * 有了動態代理之後，日誌記錄可以做的非常強大。
     * 而且與業務邏輯解耦。
     *
     * 而且使用動態代理之後，可以將日誌代碼動態的在目標方法前後進行執行。
     * 見LogUtils.java和CalculatorProxy.java
     *
     * 動態代理也有兩大問題：
     * 		1）寫起來很複雜
     * 		2）jdk默認的動態代理，如果目標對像沒有實現任何接口，是無法為它創建代理
     * 		     對象的。
     *
     * 那麼動態代理是怎麼實現的呢？
     * 		你會發現，proxy的類是：com.sun.proxy.$Proxy2
     * 		代理對像和被代理對像唯一能產生的關聯就是實現了同一個接口。
     * 		也就是說，com.sun.proxy.$Proxy2也是實現了Calculator接口。
     *
     * 動態代理很難。
     * 所以Spring實現了AOP功能，底層就是動態代理。
     * 可以利用Spring一句代碼都不寫的去創建動態代理，實現很簡單，
     * 而且沒有強制要求目標對像必須實現接口。
     *
     * 所以在現在來看這句話：
     * 將某段代碼（日誌）動態的切入（不把日誌代碼寫死在業務邏輯方法中）
     * 到指定方法（該例：加減乘除）的指定位置（方法的開始、結束、異常。。。）
     * 進行運行的這種編程方式（Spring簡化了面向切面編程）
     *
     * 為之後的AOP做準備：AOP專業術語：
     *
     * 本例中，要實現Calculator中的加減乘除方法，
     * 並在方法開始、方法返回、方法異常、方法結束的四個時間節點上，
     * 去輸出了相對應的話，即調用了LogUtils中的四個方法。
     *
     * 這裡的LogUtils叫做切面類，四個方法叫做通知方法，
     * 調用這四個方法的時間節點（就是什麼時候調用日誌記錄）叫做橫切關注點。
     * 上述的這個時間節點就叫做連接點
     * （即：每一個方法的每一個位置都是一個連接點，該例有16個連接點）
     *
     * 比如：我只需要在div方法異常、mul方法返回、add方法結束，三個連接點上進行日誌記錄
     * 這種我們真正需要執行的日誌記錄的地方，叫做切入點。
     * 即：切入點只是在眾多連接點中，我們感興趣的一部分
     *
     * 而我們怎麼選出這些切入點呢？
     * 就是運用切入點表達式。
     * 即切入點是用切入點表達式幫我們根據要求在眾多連接點中選出來的。
     * */
    @Test
    public void test() {
        Calculator calculator = new MyMathCalculator();

        calculator.add(1, 2);

        calculator.div(2, 1);

        System.out.println("[===========]");

        //如果是拿到了這個對象的代理對象，再用代理對像執行加減乘除
        Calculator proxy = CalculatorProxy.getProxy(calculator);
        proxy.add(2, 1);
        proxy.div(2, 1);
    }

}
