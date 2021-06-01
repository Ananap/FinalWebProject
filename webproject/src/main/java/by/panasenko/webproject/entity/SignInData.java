package by.panasenko.webproject.entity;

import java.io.Serializable;
import java.util.Objects;

public class SignInData implements Serializable {

    private static final long serialVersionUID = 5765928860296812438L;

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SignInData{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SignInData)) return false;
        SignInData signInData = (SignInData) o;
        return username.equals(signInData.username) &&
                password.equals(signInData.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
