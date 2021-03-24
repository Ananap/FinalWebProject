package by.panasenko.webproject.validator;

public class ValidateData {
    private static final String REGEX_DATA = "[+]?[0-9]+";

    public static boolean validateAge(String age) {
        boolean result = age.matches(REGEX_DATA);
        return result;
    }


}
