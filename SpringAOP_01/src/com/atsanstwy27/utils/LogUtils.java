package com.atsanstwy27.utils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LogUtils {

    public static void logStart(Method method, Object... args) {
        System.out.println("【" + method.getName() + "】方法開始執行，"
                + "用的參數列表【" + Arrays.asList(args) + "】");
    }

    public static void logReturn(Method method, Object result) {
        System.out.println("【" + method.getName() + "】方法執行完成，"
                + "它的計算結果是:" + result);
    }

    public static void logException(Method method, Exception e) {
        System.out.println("【" + method.getName() + "】方法執行異常，"
                + "它的異常信息是：" + e.getCause()
                + "，這個異常已經通知測試小組。");
    }

    public static void logEnd(Method method) {
        System.out.println("【" + method.getName() + "】方法最終結束了");
    }

}
