package com.atsanstwy27.proxy;


import com.atsanstwy27.inter.Calculator;
import com.atsanstwy27.utils.LogUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 幫Calculator.java生成代理對象的類
 * Object newProxyInstance
 * (ClassLoader loader,Class<?>[] interfaces, InvocationHandler h)
 */
public class CalculatorProxy {

    /**
     * 為傳入的參數對像創建一個動態代理對像
     * <p>
     * Calculator calculator: 被代理對像
     * 返回的是  代理人
     */
    public static Calculator getProxy(final Calculator calculator) {
        // TODO Auto-generated method stub

        //InvocationHandler:方法執行器，幫我們目標對像執行目標方法。
        //有了它，動態代理才有意義
        InvocationHandler h = new InvocationHandler() {
            /**
             * Object proxy：代理對象，給jdk使用，任何時候我們都不要動這個對象
             * Method method:當前將要執行的目標對像
             * Object[] args:這個方法調用時，外界傳入的參數值
             * */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args)
                    throws Throwable {

                //利用反射執行目標方法
                //目標方法執行後的返回值
                Object result = null;
                try {
                    LogUtils.logStart(method, args);
                    result = method.invoke(calculator, args);
                    LogUtils.logReturn(method, result);
                } catch (Exception e) {
                    LogUtils.logException(method, e);
                } finally {
                    LogUtils.logEnd(method);
                }

                //返回值必須返回出外界，才能拿到真正執行後的返回值
                return result;
            }

        };

        //interfaces:查詢calculator實現了的接口
        Class<?>[] interfaces = calculator.getClass().getInterfaces();

        //loader:類加載器
        ClassLoader loader = calculator.getClass().getClassLoader();

        //Proxy為目標對像創建代理對像：
        Object proxy = Proxy.newProxyInstance(loader, interfaces, h);
        return (Calculator) proxy;
    }

}
