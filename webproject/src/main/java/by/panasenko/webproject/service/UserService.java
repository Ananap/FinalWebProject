package by.panasenko.webproject.service;

import java.util.List;
import by.panasenko.webproject.entity.User;

public interface UserService {
    List<User> filterByAge(Integer ageFromValue, Integer ageToValue);
    List<User> getUserList();
    List<User> sortByName();
}
