package com.atsanstwy27.impl;

import com.atsanstwy27.inter.Calculator;

public class MyMathCalculator implements Calculator {

    /**
     * 從目前來看，如果有很多方法需要輸出日誌信息，一種辦法是定義一個通用方法，
     * 如下例的LogUtils中方法。
     * 另一種辦法是直接用syso輸出。
     * 但顯然這兩種辦法都很麻煩。如果需要更改輸出信息，需要將每一個方法中的信息
     * 更改；放在LogUtils中，同樣麻煩。再怎麼寫也都是耦合在一起。
     * */

    /**
     @Override public int add(int i, int j) {
     LogUtils.logStart(i,j);
     int result = i + j ;
     System.out.println("【add】方法運行完成，計算結果是【"+result+"】");
     return result;
     }

     @Override public int sub(int i, int j) {
     LogUtils.logStart(i,j);
     int result = i - j ;
     System.out.println("【sub】方法運行完成，計算結果是【"+result+"】");
     return result;
     }

     @Override public int mul(int i, int j) {
     LogUtils.logStart(i,j);
     int result = i * j ;
     System.out.println("【mul】方法運行完成，計算結果是【"+result+"】");
     return result;
     }

     @Override public int div(int i, int j) {
     LogUtils.logStart(i,j);
     int result = i / j ;
     System.out.println("【div】方法運行完成，計算結果是【"+result+"】");
     return result;
     }
     */

    /**
     * 解決上述問題，可以使用動態代理的辦法。
     * 見CalculatorProxy.java
     * <p>
     * 之後可以對LogUtils進行完善
     */

    @Override
    public int add(int i, int j) {
        int result = i + j;
        return result;
    }

    @Override
    public int sub(int i, int j) {
        int result = i - j;
        return result;
    }

    @Override
    public int mul(int i, int j) {
        int result = i * j;
        return result;
    }

    @Override
    public int div(int i, int j) {
        int result = i / j;
        return result;
    }

}
