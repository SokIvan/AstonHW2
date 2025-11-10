package services;



import dao.UserDao;
import dao.UserDaoInterface;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private final UserDaoInterface userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public User createUser(String name, String email, Integer age) {
        logger.info("Creating new user: {}", name);
        User user = new User(name, email, age);
        return userDao.save(user);
    }

    public Optional<User> getUserById(Long id) {
        logger.info("Retrieving user by id: {}", id);
        return userDao.findById(id);
    }

    public List<User> getAllUsers() {
        logger.info("Retrieving all users");
        return userDao.findAll();
    }

    public User updateUser(Long id, String name, String email, Integer age) {
        logger.info("Updating user with id: {}", id);

        Optional<User> existingUser = userDao.findById(id);
        if (existingUser.isEmpty()) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }

        User user = existingUser.get();
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        user.setUpdatedAt();

        return userDao.update(user);
    }

    public boolean deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);

        Optional<User> existingUser = userDao.findById(id);
        if (existingUser.isEmpty()) {
            return false;
        }

        userDao.delete(id);
        return true;
    }
}