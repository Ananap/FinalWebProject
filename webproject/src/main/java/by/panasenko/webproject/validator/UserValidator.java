package by.panasenko.webproject.validator;

import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.entity.User;
import by.panasenko.webproject.util.RegexpPropertyUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String REGEXP_PHONE_NUM = "regexp.phone_number";
    private static final String REGEXP_USER_FIO = "regexp.user_fio";
    private static final String REGEXP_USERNAME = "regexp.username";
    private static final String REGEXP_EMAIL = "regexp.email";

    private static final RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

    public static boolean validate(SignUpData signUpData) {
        return validateData(signUpData.getUsername(), signUpData.getFirstName(), signUpData.getLastName(), signUpData.getPhoneNumber(), signUpData.getEmail());
    }

    public static boolean validate(User user) {
        return validateData(user.getUsername(), user.getFirstName(), user.getLastName(), user.getPhone(), user.getEmail());
    }

    private static boolean validateData(String username, String firstName, String lastName, String phoneNumber, String email) {
        return validateUsername(username) || validateFIO(firstName) || validateFIO(lastName) || validatePhoneNumber(phoneNumber) || !validateEmail(email);
    }

    private static boolean validateUsername(String username) {
        return isMatchFounded(username, regexpPropertyUtil.getProperty(REGEXP_USERNAME));
    }

    private static boolean validateFIO(String fio) {
        return isMatchFounded(fio, regexpPropertyUtil.getProperty(REGEXP_USER_FIO));
    }

    private static boolean validatePhoneNumber(String phoneNumber) {
        return isMatchFounded(phoneNumber, regexpPropertyUtil.getProperty(REGEXP_PHONE_NUM));
    }

    public static boolean validateEmail(String email) {
        return isMatchFounded(email, regexpPropertyUtil.getProperty(REGEXP_EMAIL));
    }

    private static boolean isMatchFounded(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
}