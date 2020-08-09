package com.atsanstwy27;

import com.atsanstwy27.bean.Person;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOCTest1 {

    private ApplicationContext ioc=new ClassPathXmlApplicationContext("conf/ioc1.xml");

    /**
     * 實驗2：根據bean的類型從IOC容器中獲取bean的實例
     * 如果ioc容器中這個類型的bean有多個，那麼用第一種方式查找就會報錯。
     * */
    @Test
    public void test02() {
        //Person bean=ioc.getBean(Person.class);
        //System.out.println(bean);

        //還可以這樣：
        Person bean2 = ioc.getBean("person02", Person.class);
        System.out.println(bean2);

        Object bean3 = ioc.getBean("person03");
        System.out.println(bean3);

        Object bean4 = ioc.getBean("person04");
        System.out.println(bean4);
    }

    /**
     * 從容器中拿到這個組件
     */
    @Test
    public void test01() {
        //創建對象是容器做的
        Person bean = (Person) ioc.getBean("person01");

        System.out.println(bean);
    }

}
