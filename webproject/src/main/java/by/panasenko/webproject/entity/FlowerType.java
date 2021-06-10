package by.panasenko.webproject.entity;

public class FlowerType {
    private int id;
    private FlowerCategory category;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FlowerCategory getCategory() {
        return category;
    }

    public void setCategory(FlowerCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
