package by.panasenko.webproject.entity;

import java.util.List;

public class FlowerType {
    private int id;
    private FlowerCategory category;
    private List<Flower> flower;
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

    public List<Flower> getFlower() {
        return flower;
    }

    public void setFlower(List<Flower> flower) {
        this.flower = flower;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
