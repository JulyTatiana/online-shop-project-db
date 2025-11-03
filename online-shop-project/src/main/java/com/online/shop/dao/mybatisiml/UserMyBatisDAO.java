package com.online.shop.dao.mybatisiml;

import com.online.shop.model.User;
import com.online.shop.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class UserMyBatisDAO {

    public User findById(int userId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.findById(userId);
        }
    }

    public List<User> findAll() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.findAll();
        }
    }

    public void insert(User user) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.insert(user);
        }
    }

    public void update(User user) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.update(user);
        }
    }

    public void delete(int userId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.delete(userId);
        }
    }
}
