package com.example.dynamicdatasource.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int findUserCount();
}
