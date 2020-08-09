package com.atsanstwy27;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOC3Test {

    private ApplicationContext ioc2 = new ClassPathXmlApplicationContext("conf/ioc3.xml");

    @Test
    public void test06() {
        Object bean = ioc2.getBean("airPlane01");
        System.out.println(bean);

        Object bean1 = ioc2.getBean("airPlane02");
        System.out.println(bean1);

        System.out.println("容器啟動完成....");
    }

}
