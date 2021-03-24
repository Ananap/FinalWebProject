package by.panasenko.webproject.service.impl;

import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.repository.AgeUserSpecification;
import by.panasenko.webproject.repository.Specification;
import by.panasenko.webproject.repository.UserRepository;
import by.panasenko.webproject.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private UserRepository userRepository;

    public UserServiceImpl() {
        userRepository = new UserRepository();
    }

    @Override
    public List<User> filterByAge(Integer ageFromValue, Integer ageToValue) {
        Specification<User> spec = new AgeUserSpecification(ageFromValue, ageToValue);
        return userRepository.query(spec);
    }

    @Override
    public List<User> getUserList() {
        return userRepository.getUserList();
    }

    @Override
    public List<User> sortByName() {
        return userRepository.sort();
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }
}
