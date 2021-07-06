package by.panasenko.webproject.validator;

import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.util.RegexpPropertyUtil;

public class UserValidator {
    private static final String REGEXP_PHONE_NUM = "regexp.phone_number";
    private static final String REGEXP_USER_FIO = "regexp.user_fio";
    private static final String REGEXP_USERNAME = "regexp.username";
    private static final String REGEXP_EMAIL = "regexp.email";
    private static final String REGEXP_PASSWORD = "regexp.password";
    private static final RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

    public static boolean validate(SignUpData signUpData) {
        return validateData(signUpData.getUsername(), signUpData.getFirstName(), signUpData.getLastName(), signUpData.getPhoneNumber(), signUpData.getEmail());
    }

    public static boolean validate(User user) {
        return validateData(user.getUsername(), user.getFirstName(), user.getLastName(), user.getPhone(), user.getEmail());
    }

    private static boolean validateData(String username, String firstName, String lastName, String phoneNumber, String email) {
        return validateUsername(username) && validateFIO(firstName) && validateFIO(lastName) && validatePhoneNumber(phoneNumber) && validateEmail(email);
    }

    public static boolean validateUsername(String username) {
        return isMatchFounded(username, regexpPropertyUtil.getProperty(REGEXP_USERNAME));
    }

    public static boolean validateFIO(String fio) {
        return isMatchFounded(fio, regexpPropertyUtil.getProperty(REGEXP_USER_FIO));
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return isMatchFounded(phoneNumber, regexpPropertyUtil.getProperty(REGEXP_PHONE_NUM));
    }

    public static boolean validateEmail(String email) {
        return isMatchFounded(email, regexpPropertyUtil.getProperty(REGEXP_EMAIL));
    }

    public static boolean validatePassword(String password) {
        return isMatchFounded(password, regexpPropertyUtil.getProperty(REGEXP_PASSWORD));
    }

    private static boolean isMatchFounded(String text, String regex) {
        return (text != null) ? text.matches(regex) : false;
    }
}