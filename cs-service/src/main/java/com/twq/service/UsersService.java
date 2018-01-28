package com.twq.service;

import com.twq.dao.mapper.UsersMapper;
import com.twq.dao.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {


    @Autowired
    private UsersMapper usersMapper;


    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return
     */
    public Users getUserById(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }
}
