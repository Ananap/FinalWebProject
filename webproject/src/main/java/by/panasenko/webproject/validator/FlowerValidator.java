package by.panasenko.webproject.validator;

import by.panasenko.webproject.util.RegexpPropertyUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlowerValidator {
    private static final String REGEXP_FLOWER_TYPE_ID = "regexp.flowerTypeId";
    private static final String REGEXP_FLOWER_ID = "regexp.id";

    private static final RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

    public static boolean validateFlowerTypeId(String flowerTypeId) {
        return isMatchFounded(flowerTypeId, regexpPropertyUtil.getProperty(REGEXP_FLOWER_TYPE_ID));
    }

    public static boolean validateFlowerId(String flowerId) {
        return isMatchFounded(flowerId, regexpPropertyUtil.getProperty(REGEXP_FLOWER_ID));
    }

    private static boolean isMatchFounded(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
}
