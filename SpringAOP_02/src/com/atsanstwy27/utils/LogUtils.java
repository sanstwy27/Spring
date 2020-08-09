package com.atsanstwy27.utils;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(1)
public class LogUtils {

    /**
     * 抽取可重用的切入點表達式：
     * 1、隨便聲明一個沒有實現的返回void的空方法
     * 2、給方法上標注@Pointcut註解
     */
    @Pointcut("execution(public int com.atsanstwy27.MyMath*r.*(..))")
    public void MyPoint() {
    }

    ;

    //想在執行目標方法之前運行
    @Before("MyPoint()")
    public static void logStart(JoinPoint joinPoint) {
        //獲取到目標方法運行時使用的參數
        Object[] args = joinPoint.getArgs();
        //獲取到方法簽名
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("LogUtils前置【" + name + "】方法開始執行，"
                + "用的參數列表【" + Arrays.asList(args) + "】");
    }

    //想在目標方法正常執行完成之後運行
    @AfterReturning(value = "MyPoint()", returning = "result")
    public static void logReturn(JoinPoint joinPoint, Object result) {
        //獲取到方法簽名
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("LogUtils返回【" + name + "】方法執行完成，"
                + "它的計算結果是:" + result);
    }

    //想在目標方法出現異常的時候運行
    @AfterThrowing(value = "MyPoint()", throwing = "exception")
    public static void logException(JoinPoint joinPoint, Exception exception) {
        //獲取到方法簽名
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("LogUtils異常【" + name + "】方法執行異常，"
                + "它的異常信息是：" + exception
                + "，這個異常已經通知測試小組。");
    }

    //想在目標方法結束的時候運行
    @After("MyPoint()")
    public static void logEnd(JoinPoint joinPoint) {
        //獲取到方法簽名
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        System.out.println("LogUtils後置【" + name + "】方法最終結束了");
    }

    /**
     * @Around：環繞 ：是Spring中強大的通知；（其實就是動態代理）
     * <p>
     * try {
     * @Before result = method.invoke(calculator,args);
     * @AfterReturning } catch (Exception e) {
     * @AfterThrowing }finally{
     * @After }
     * <p>
     * 以上四種通知合在一起（四合一）就是環繞通知。
     * <p>
     * 它能這麼用，就是因為環繞通知中有一個參數ProceedingJoinPoint pjp
     * <p>
     * 而且環繞通知的順序是正確的@Before => @AfterReturning => @After
     * <p>
     * 也就是說，這兩種辦法選其中一種就可以實現功能。
     * 如果現在這兩種辦法一起用（沒有註釋掉）
     * 那麼環繞通知會優先於普通通知執行。執行順序：
     * <p>
     * 【環繞前置通知】【add方法開始】
     * LogUtils前置【add】方法開始執行，用的參數列表【[3, 5]】
     * 方法內部執行
     * 【環繞返回通知】【add方法返回，返回值8】
     * 【環繞後置通知】【add方法結束】
     * LogUtils後置【add】方法最終結束了
     * LogUtils返回【add】方法執行完成，它的計算結果是:8
     */
    @Around("MyPoint()")
    public Object myAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        String name = signature.getName();

        Object proceed = null;
        try {
            System.out.println("【環繞前置通知】【" + name + "方法開始】");
            //就是利用反射調用目標方法即可，就是method.invoke(obj,args)
            proceed = pjp.proceed(args);
            System.out.println("【環繞返回通知】【" + name + "方法返回，返回值" + proceed + "】");
        } catch (Exception e) {
            System.out.println("【環繞異常通知】【" + name + "方法異常】，異常信息" + e);
            //為了讓外界知道這個異常，這個異常一定拋出去
            throw new RuntimeException(e);
        } finally {
            System.out.println("【環繞後置通知】【" + name + "方法結束】");
        }
        //反射調用後的返回值也一定返回出去
        return proceed;
    }

}
