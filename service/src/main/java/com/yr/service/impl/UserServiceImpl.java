package com.yr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yr.entity.User;
import com.yr.mapper.UserMapper;
import com.yr.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    public User getUpdate(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> select() {
        return userMapper.selectList(null);
    }

}
