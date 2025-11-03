package com.online.shop.service.Implementation;

import com.online.shop.dao.IUserDAO;
import com.online.shop.dao.implementationDAO.UserDAOImplementation;
import com.online.shop.model.User;
import com.online.shop.service.IUserService;

public class UserServiceImplementation implements IUserService {

    private IUserDAO userDAO= new UserDAOImplementation();

    @Override
    public User getUserById(int userId) {
        return userDAO.findById(userId);
    }

    @Override
    public boolean createUser(User user) {
        return userDAO.insert(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(int userId) {
        return userDAO.delete(userId);
    }
}
