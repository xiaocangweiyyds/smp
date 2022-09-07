package com.yr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yr.entity.User;

import java.util.List;

public interface IUserService extends IService<User> {

    public void add(User user);

    public void delete(Integer id);

    public void update(User user);

    public User getUpdate(int id);

    public List<User> select();

}
