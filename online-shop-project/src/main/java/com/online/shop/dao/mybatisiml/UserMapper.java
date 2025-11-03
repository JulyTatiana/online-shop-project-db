package com.online.shop.dao.mybatisiml;

import com.online.shop.model.User;
import java.util.List;

public interface UserMapper {
    User findById(int userId);
    List<User> findAll();
    void insert(User user);
    void update(User user);
    void delete(int userId);
}
