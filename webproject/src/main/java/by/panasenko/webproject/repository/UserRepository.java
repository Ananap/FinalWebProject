package by.panasenko.webproject.repository;

import by.panasenko.webproject.entity.Creator;
import by.panasenko.webproject.entity.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository {
    private List<User> userList;

    public UserRepository() {
        userList = Creator.createUsers();
    }

    public List<User> getUserList() {
        return Collections.unmodifiableList(userList);
    }

    public List<User> query(Specification<User> specification) {
        List<User> list = userList.stream().filter(specification::specify).collect(Collectors.toList());
        return list;
    }

    public List<User> sort() {
        List<User> sortedList = userList.stream().sorted(Comparator.comparing(User::getName)).collect(Collectors.toList());
        return sortedList;
    }
}
