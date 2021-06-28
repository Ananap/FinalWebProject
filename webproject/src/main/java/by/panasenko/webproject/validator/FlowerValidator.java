package by.panasenko.webproject.validator;

import by.panasenko.webproject.entity.FlowerCategory;
import by.panasenko.webproject.entity.Soil;
import by.panasenko.webproject.util.RegexpPropertyUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlowerValidator {
    private static final String REGEXP_FLOWER_ID = "regexp.id";
    private static final String REGEXP_FLOWER_TYPE_ID = "regexp.flowerTypeId";
    private static final String REGEXP_FLOWER_NAME = "regexp.flower.name";
    private static final String REGEXP_FLOWER_DESCRIPTION = "regexp.flower.description";
    private static final String REGEXP_FLOWER_PRICE = "regexp.flower.price";
    private static final String REGEXP_FLOWER_QUANTITY = "regexp.flower.quantity";
    private static final String REGEXP_FLOWER_LIGHT = "regexp.flower.light";
    private static final String REGEXP_FLOWER_WATERING = "regexp.flower.watering";

    private static final RegexpPropertyUtil regexpPropertyUtil = RegexpPropertyUtil.getInstance();

    public static boolean validateData(String nameFlower, String description, String price, String soil, String watering, String light) {
        if (!validateName(nameFlower)) {
            return false;
        }
        if (!validateDescription(description)) {
            return false;
        }
        if (!validatePrice(price)) {
            return false;
        }
        if (!validateSoil(soil)) {
            return false;
        }
        if (!validateWatering(watering)) {
            return false;
        }
        return validateLight(light);
    }

    public static boolean validateName(String name) {
        return isMatchFounded(name, regexpPropertyUtil.getProperty(REGEXP_FLOWER_NAME));
    }

    public static boolean validateDescription(String description) {
        return isMatchFounded(description, regexpPropertyUtil.getProperty(REGEXP_FLOWER_DESCRIPTION));
    }

    public static boolean validatePrice(String price) {
        return isMatchFounded(price, regexpPropertyUtil.getProperty(REGEXP_FLOWER_PRICE));
    }

    public static boolean validateLight(String light) {
        return isMatchFounded(light, regexpPropertyUtil.getProperty(REGEXP_FLOWER_LIGHT));
    }

    public static boolean validateWatering(String watering) {
        return isMatchFounded(watering, regexpPropertyUtil.getProperty(REGEXP_FLOWER_WATERING));
    }

    public static boolean validateFlowerTypeId(String flowerTypeId) {
        return isMatchFounded(flowerTypeId, regexpPropertyUtil.getProperty(REGEXP_FLOWER_TYPE_ID));
    }

    public static boolean validateId(String flowerId) {
        return isMatchFounded(flowerId, regexpPropertyUtil.getProperty(REGEXP_FLOWER_ID));
    }

    public static boolean validateQuantity(String count) {
        return isMatchFounded(count, regexpPropertyUtil.getProperty(REGEXP_FLOWER_QUANTITY));
    }

    public static boolean isMatchFounded(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public static boolean validateSoil(String soil) {
        try {
            Soil.valueOf(soil);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public static boolean validateCategory(String category) {
        try {
            FlowerCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
