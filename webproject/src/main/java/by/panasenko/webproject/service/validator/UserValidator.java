package by.panasenko.webproject.service.validator;

import by.panasenko.webproject.entity.SignUpData;
import by.panasenko.webproject.util.RegexpPropertyUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String REGEXP_PHONE_NUM = "regexp.phone_number";
    private static final String REGEXP_USER_FIO = "regexp.user_fio";
    private static final String REGEXP_USERNAME = "regexp.username";
    private static final String REGEXP_EMAIL = "regexp.email";

    private static final UserValidator instance = new UserValidator();
    private static final RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

    private UserValidator() {
    }

    public static UserValidator getInstance() {
        return instance;
    }

    public boolean validate(SignUpData signUpData) {
        return validateData(signUpData.getUsername(), signUpData.getFirstName(), signUpData.getLastName(), signUpData.getPhoneNumber(), signUpData.getEmail());
    }

/*    public boolean validate(User user) {
        return validateData(user.getLogin(), user.getName(), user.getSurname(), user.getPatronymic(), user.getPhoneNumber());
    }*/

    private boolean validateData(String username, String firstName, String lastName, String phoneNumber, String email) {
        if (!validateUsername(username)) {
            return false;
        }
        if (!validateFIO(firstName)) {
            return false;
        }
        if (!validateFIO(lastName)) {
            return false;
        }
        if (!validatePhoneNumber(phoneNumber)) {
            return false;
        }
        return validateEmail(email);
    }

    private boolean validateUsername(String username) {
        return isMatchFounded(username, regexpPropertyUtil.getProperty(REGEXP_USERNAME));
    }

    private boolean validateFIO(String fio) {
        return isMatchFounded(fio, regexpPropertyUtil.getProperty(REGEXP_USER_FIO));
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        return isMatchFounded(phoneNumber, regexpPropertyUtil.getProperty(REGEXP_PHONE_NUM));
    }

    private boolean validateEmail(String email) {
        return isMatchFounded(email, regexpPropertyUtil.getProperty(REGEXP_EMAIL));
    }

    private boolean isMatchFounded(String text, String regex) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        return matcher.find();
    }
}