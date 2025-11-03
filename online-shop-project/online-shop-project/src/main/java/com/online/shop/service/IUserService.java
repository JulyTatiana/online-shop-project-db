package com.online.shop.service;

import com.online.shop.model.User;

public interface IUserService {
    User getUserById(int userId);
    boolean createUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int userId);
}
