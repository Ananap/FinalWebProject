package by.panasenko.webproject.entity;

import com.sun.tools.javac.util.List;

public class Creator {
    public static List<User> createUsers() {
        List<User> users = List.of(
                new User(1, "Natasha", 34),
                new User(2, "Anna", 2),
                new User(3, "Veronika", 20),
                new User(4, "Nikita", 60),
                new User(5, "Vadim", 15),
                new User(6, "Danila", 75));
        return users;
    }
}
