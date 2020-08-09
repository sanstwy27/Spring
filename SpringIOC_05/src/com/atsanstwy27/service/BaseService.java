package com.atsanstwy27.service;

import com.atsanstwy27.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<T> {

    @Autowired
    BaseDao<T> baseDao;

    public void save() {
        System.out.println("自動注入的dao：" + baseDao);
        baseDao.save();
    }

}