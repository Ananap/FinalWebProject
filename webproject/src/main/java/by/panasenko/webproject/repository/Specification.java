package by.panasenko.webproject.repository;

import by.panasenko.webproject.entity.User;

public interface Specification <T extends User>{
    boolean specify(T t);
}
