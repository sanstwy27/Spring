/****
 * [Reference]
 * Course: http://www.atguigu.com/
 * Source: https://github.com/soyoungboy
 */
package com.atsanstwy27;

import com.atsanstwy27.bean.Employee;
import com.atsanstwy27.dao.EmployeeDao;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxTest {

    ApplicationContext ioc = new ClassPathXmlApplicationContext("conf/applicationContext.xml");
    //不支持具名參數的功能
    JdbcTemplate jdbcTemplate = ioc.getBean(JdbcTemplate.class);
    //支持具名參數功能的jdbcTemplate
    NamedParameterJdbcTemplate njp = ioc.getBean(NamedParameterJdbcTemplate.class);

    @Test
    public void test() throws SQLException {
        DataSource dataSource = ioc.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    @Test
    public void test01(){
        DataSource dataSource = ioc.getBean(DataSource.class);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void test02(){
        System.out.println(jdbcTemplate);
    }

    /**
     * 實驗2：將emp_id=5的記錄的salary字段更新為1300.00
     */
    @Test
    public void jdbcTemplate02(){
        String sql = "UPDATE employee SET salary=? WHERE emp_id=?";
        int i = jdbcTemplate.update(sql, 1300.12,5);
        System.out.println(i);

    }

    /**
     * 實驗3：批量插入
     * Object[][]:
     * 第一維的長度代表sql執行的此時
     * 第二維是保存當次執行使用的參數
     */
    @Test
    public void jdbcTemplate03(){
        String sql = "INSERT INTO employee(emp_name,salary) VALUES(?,?)";
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{"張三",1234});
        batchArgs.add(new Object[]{"張三2",12345});
        batchArgs.add(new Object[]{"張三3",12346});
        int[] is = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println("完成...");
    }

    /**
     * 實驗4：查詢emp_id=5的數據庫記錄，封裝為一個Java對像返回
     *
     * 查單個對象：queryForObject傳入一個BeanPropertyRowMapper
     */
    @Test
    public void jdbcTemplate04(){
        String sql = "SELECT emp_id id,emp_name empName,salary FROM employee WHERE emp_id=?";
        //BeanHandler
        Employee employee = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employee.class), 5);
        System.out.println(employee);
    }

    /**
     * 實驗5：查詢salary>4000的數據庫記錄，封裝為List集合返回
     *
     * 查集合：query方法傳入BeanPropertyRowMapper表示集合裡面元素的類型
     */
    @Test
    public void jdbcTemplate05(){
        String sql = "SELECT emp_id id,emp_name empName,salary FROM employee WHERE salary>?";
        List<Employee> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class), 4000);
        for (Employee employee : list) {
            System.out.println(employee);
        }
    }

    /**
     * 實驗6：查詢最大salary
     */
    @Test
    public void jdbcTemplate06(){
        String sql = "SELECT MAX(salary) FROM employee";
        Double max = jdbcTemplate.queryForObject(sql, Double.class);
        System.out.println(max);
    }

    /**
     * 實驗7：使用帶有[具名參數]的SQL語句插入一條員工記錄，並以Map形式傳入參數值
     *
     * [具名參數]：具有名字的參數
     *         以前都是?佔位符的形式；不太人性化
     *
     * 為參數的位置起名；   [:自定義名]
     *     "INSERT INTO employee(emp_name,salary) VALUES(:empname,:salary)";
     *
     *
     */
    @Test
    public void jdbcTemplate07(){
        String sql = "INSERT INTO employee(emp_name,salary) VALUES(:empname,:salary)";
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("empname", "哈哈哈");
        maps.put("salary", 998);

        int update = njp.update(sql, maps);
        System.out.println(update);

    }

    /**
     * BookService.saveBook(book)
     * 實驗8：重複實驗7，以SqlParameterSource形式傳入參數值
     * BookDao.saveBook(sql,book.getId,book.getName)
     */
    @Test
    public void jdbcTemplate08(){
        Employee employee = new Employee();
        employee.setEmpName("張三1234");
        employee.setSalary(9989.98);

        String sql = "INSERT INTO employee(emp_name,salary) VALUES(:empName,:salary)";
        int update = njp.update(sql, new BeanPropertySqlParameterSource(employee));
        System.out.println(update);

    }

    /**
     * 實驗9：創建BookDao，自動裝配JdbcTemplate對像
     */
    @Test
    public void test09(){
        EmployeeDao employeeDao = ioc.getBean(EmployeeDao.class);
        Employee employee = new Employee();
        employee.setEmpName("dshkjahdajk");
        employee.setSalary(9998.98);
        employeeDao.saveEmployee(employee);
    }

}
