package by.panasenko.webproject.entity;

import java.io.Serializable;
import java.util.Objects;

public class SignInData implements Serializable {

    private static final long serialVersionUID = 5765928860296812438L;

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        sb.append("email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SignInData)) return false;
        SignInData signInData = (SignInData) o;
        return email.equals(signInData.email) &&
                password.equals(signInData.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
