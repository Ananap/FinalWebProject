package by.panasenko.webproject.util;

public enum PagePath {
    MAIN_PAGE("/pages/main.jsp"),
    ERROR_PAGE("/pages/error.jsp");

    String value;

    PagePath(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
