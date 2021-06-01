package by.panasenko.webproject.entity;

public enum Status {
    APPROVED("Одобрено"),
    INPROCESS("В обработке"),
    REJECTED("Отклонен");

    String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
