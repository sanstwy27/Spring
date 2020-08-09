package com.atsanstwy27;

import com.atsanstwy27.servlet.BookServlet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 使用Spring的單元測試，
 * 1）導包，Spring的單元測試包spring-test-4.0.0.RELEASE.jar
 * 2）@ContextConfiguration(locations="")使用它來指定Spring的配置文件的位置
 * 3）@RunWith()指定用哪種驅動進行單元測試，默認就是junit
 * 	@RunWith(SpringJUnit4ClassRunner.class)
 *  使用Spring的單元測試模塊來執行標了@Test註解的測試方法。
 * */
@ContextConfiguration(locations= "classpath:conf/ioc4.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class IOC4Test {

    //ApplicationContext ioc=new ClassPathXmlApplicationContext("conf/ioc4.xml");
    @Autowired
    ApplicationContext ioc=null;

    @Autowired
    BookServlet bookServlet;

    @Test
    public void test03() {
        System.out.println(bookServlet);
    }

    /**
     * 使用註解加入到容器中的組件，和使用配置加入到容器中的組件行為都是默認一樣的
     * 1、組件的id，默認是組件的類名首字母小寫
     * 2、組件的作用域，默認就是單例的
     * */
    @Test
    public void test01() {
        Object bean = ioc.getBean("bookDao");
        Object bean2 = ioc.getBean("bookDao");
        System.out.println(bean == bean2);
    }

    @Test
    public void test02() {
        BookServlet bookServlet = ioc.getBean(BookServlet.class);
        bookServlet.doGet();
    }

}
