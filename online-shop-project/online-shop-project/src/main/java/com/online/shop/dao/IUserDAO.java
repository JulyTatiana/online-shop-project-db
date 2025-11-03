package com.online.shop.dao;

import com.online.shop.model.User;

public interface IUserDAO {
    User findById(Integer userId);
    boolean insert(User user);
    boolean update(User user);
    boolean delete(Integer userId);
}
