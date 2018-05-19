package com.example.dynamicdatasource.service.impl;


import com.example.dynamicdatasource.commons.datasourceconfig.TargetDataSource;
import com.example.dynamicdatasource.dao.UserMapper;
import com.example.dynamicdatasource.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public int findUserCountFromDb1() {
        return userMapper.findUserCount();
    }

    @Override
    @TargetDataSource("dataSourceTwo")
    public int findUserCountFromDb2() {
        return userMapper.findUserCount();
    }
}
