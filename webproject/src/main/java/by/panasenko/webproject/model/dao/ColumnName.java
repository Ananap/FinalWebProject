package by.panasenko.webproject.model.dao;

public final class ColumnName {
    /*User Table*/
    public static final String USERS_ID = "user_id";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_EMAIL = "email";
    public static final String USERS_ROLE = "user_role";
    public static final String USERS_FIRST_NAME = "first_name";
    public static final String USERS_LAST_NAME = "last_name";
    public static final String USERS_ADDRESS = "address";
    public static final String USERS_PHONE = "phone";

    /*FlowerType table*/
    public static final String FLOWER_TYPE_ID = "type_id";
    public static final String FLOWER_TYPE_CATEGORY = "category";
    public static final String FLOWER_TYPE_DESCRIPTION = "type_description";

    /*Flower table*/
    public static final String FLOWER_ID = "id";
    public static final String FLOWER_NAME = "name";
    public static final String FLOWER_DESCRIPTION = "description";
    public static final String FLOWER_PRICE = "price";
    public static final String FLOWER_IMAGE = "flower_image";
    public static final String SOIL = "soil";
    public static final String FLOWER_WATERING = "watering";
    public static final String FLOWER_COUNTRY = "origin";
    public static final String FLOWER_LIGHT = "light";

    /*Storage table*/
    public static final String STORAGE_ID = "storage_id";
    public static final String STORAGE_COUNT = "storage_count";

    /*Basket table*/
    public static final String BASKET_ID = "id";
    public static final String BASKET_TOTAL_COST = "total_cost";

    /*BasketFlower table*/
    public static final String BASKET_FLOWER_ID = "basket_flower_id";
    public static final String BASKET_FLOWER_BASKET_ID = "basket_id";
    public static final String BASKET_FLOWER_FLOWER_ID = "flower_id";
    public static final String BASKET_FLOWER_COUNT = "count";
    public static final String BASKET_FLOWER_SUB_TOTAL = "sub_total";

    private ColumnName() {
    }
}
