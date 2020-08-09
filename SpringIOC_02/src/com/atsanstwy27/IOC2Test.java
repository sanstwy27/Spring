package com.atsanstwy27;

import com.atsanstwy27.bean.Book;
import com.atsanstwy27.bean.Car;
import com.atsanstwy27.bean.Person;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

public class IOC2Test {

    private ApplicationContext ioc2 = new ClassPathXmlApplicationContext("conf/ioc2.xml");

    @Test
    public void test05() {
        Person person06 = (Person) ioc2.getBean("person06");
        System.out.println(person06);

        Object bean = ioc2.getBean("book08");
        Object bean2 = ioc2.getBean("book08");
        System.out.println(bean == bean2);
    }

    @Test
    public void test04() {
        Person person03 = (Person) ioc2.getBean("person03");
        Map<String, Object> maps = person03.getMaps();
        System.out.println(maps);

        Map<String, Object> bean = (Map<String, Object>) ioc2.getBean("myMap");
        System.out.println(bean.getClass());

        /**
         * 級聯屬性可以修改屬性的屬性，注意：原來的bean的值可能會被修改
         * */
        Person person04 = (Person) ioc2.getBean("person04");
        Object car = ioc2.getBean("car01");
        System.out.println("容器中的car：" + car);
        System.out.println("Person中的car:" + person04.getCar());
    }

    /**
     * 實驗4：正確的為各種屬性賦值
     * 測試使用null值、默認引用類型就是null，基本類型是默認值
     */
    @Test
    public void test03() {
        Person bean = (Person) ioc2.getBean("person01");
        System.out.println(bean.getLastName() == null);
        System.out.println(bean.getCar());

        Car car = bean.getCar();
        Object bean2 = ioc2.getBean("car01");
        System.out.println(bean2 == car);

        Person bean3 = (Person) ioc2.getBean("person02");
        List<Book> books = bean3.getBooks();
        System.out.println(books);
        System.out.println("======================");

        Map<String, Object> maps = bean3.getMaps();
        System.out.println(maps);
        System.out.println("======================");
        System.out.println(bean3.getProperties());
    }

}
