package by.panasenko.webproject.entity;

import java.util.Objects;

public class User {
    private long userId;
    private String name;
    private int age;

    public User() {
    }

    public User(long id, String name, int age) {
        this.userId = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return userId;
    }

    public void setId(long id) {
        this.userId = id;
    }

    public void setId(int id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (age != user.age) return false;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }
}
