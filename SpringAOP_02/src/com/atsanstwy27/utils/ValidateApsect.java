package com.atsanstwy27.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(2)
public class ValidateApsect {

    /**
     * 第二個切面
     * 這樣一來，不包括環繞通知的話，輸出順序是：
     * LogUtils前置【add】方法開始執行，用的參數列表【[3, 5]】
     * ValidateApsect前置【add】方法開始執行，用的參數列表【[3, 5]】
     * 方法內部執行
     * ValidateApsect後置【add】方法最終結束了
     * ValidateApsect返回【add】方法執行完成，它的計算結果是:8
     * LogUtils後置【add】方法最終結束了
     * LogUtils返回【add】方法執行完成，它的計算結果是:8
     * <p>
     * 為什麼先LogUtils是因為，它是按照首字母順序排列的。但無論怎麼排，
     * 永遠都是先執行的後結束，後執行的先結束，如上所示。與環繞的不同。
     * 除了按照首字母順序排以外，還有其他辦法調整輸出順序。
     * 就是加上一個標籤@Order(1) //使用Order改變切面順序，數值越小，優先級越高。
     * <p>
     * 之後再在LogUtils加上環繞通知後，執行順序：
     * 【環繞前置通知】【add方法開始】
     * LogUtils前置【add】方法開始執行，用的參數列表【[3, 5]】
     * ValidateApsect前置【add】方法開始執行，用的參數列表【[3, 5]】
     * 方法內部執行
     * ValidateApsect後置【add】方法最終結束了
     * ValidateApsect返回【add】方法執行完成，它的計算結果是:8
     * 【環繞返回通知】【add方法返回，返回值8】
     * 【環繞後置通知】【add方法結束】
     * LogUtils後置【add】方法最終結束了
     * LogUtils返回【add】方法執行完成，它的計算結果是:8
     * 也就是說，環繞只影響當前切面
     */
    @Before("com.atsanstwy27.utils.LogUtils.MyPoint()")
    public void logStart(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("ValidateApsect前置【" + name + "】方法開始執行，"
                + "用的參數列表【" + Arrays.asList(args) + "】");
    }

    @AfterReturning(value = "com.atsanstwy27.utils.LogUtils.MyPoint()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("ValidateApsect返回【" + name + "】方法執行完成，"
                + "它的計算結果是:" + result);
    }

    @AfterThrowing(value = "com.atsanstwy27.utils.LogUtils.MyPoint()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("ValidateApsect異常【" + name + "】方法執行異常，"
                + "它的異常信息是：" + exception
                + "，這個異常已經通知測試小組。");
    }

    @After("com.atsanstwy27.utils.LogUtils.MyPoint()")
    public void logEnd(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("ValidateApsect後置【" + name + "】方法最終結束了");
    }

}
