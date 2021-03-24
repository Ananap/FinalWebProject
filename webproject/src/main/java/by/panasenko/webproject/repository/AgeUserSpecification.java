package by.panasenko.webproject.repository;

import by.panasenko.webproject.entity.User;

public class AgeUserSpecification implements Specification<User> {
    private int from;
    private int to;

    public AgeUserSpecification(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean specify(User user) {
        int age = user.getAge();
        boolean result = age >= from && age <= to;
        return result;
    }
}
