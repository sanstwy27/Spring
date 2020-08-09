package com.atsanstwy27.dao;

import com.atsanstwy27.bean.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User> {

    @Override
    public void save() {
        // TODO Auto-generated method stub
        System.out.println("UserDao...保存用戶....");
    }

}