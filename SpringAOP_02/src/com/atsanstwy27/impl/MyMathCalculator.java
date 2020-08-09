package com.atsanstwy27.impl;

import com.atsanstwy27.inter.Calculator;
import org.springframework.stereotype.Service;

@Service
public class MyMathCalculator implements Calculator {
//public class MyMathCalculator {

    public int add(int i, int j) {
        System.out.println("方法內部執行");
        int result = i + j;
        return result;
    }

    public int sub(int i, int j) {
        System.out.println("方法內部執行");
        int result = i - j;
        return result;
    }

    public int mul(int i, int j) {
        System.out.println("方法內部執行");
        int result = i * j;
        return result;
    }

    public int div(int i, int j) {
        System.out.println("方法內部執行");
        int result = i / j;
        return result;
    }

}
