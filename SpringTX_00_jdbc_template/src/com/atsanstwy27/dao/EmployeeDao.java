package com.atsanstwy27.dao;

import com.atsanstwy27.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveEmployee(Employee employee) {
        String sql = "INSERT INTO employee(emp_name,salary) VALUES(?,?)";
        jdbcTemplate.update(sql, employee.getEmpName(), employee.getSalary());
    }
}
