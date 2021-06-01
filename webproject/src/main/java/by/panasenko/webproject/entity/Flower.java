package by.panasenko.webproject.entity;

import java.util.List;

public class Flower {
    private int id;
    private String name;
    private String description;
    private double price;
    private Soil soil;
    private int watering;
    private boolean light;
    private String originCountry;
    private String flowerImage;
    private FlowerType flowerType;
    private List<OrderFlower> orders;
    private List<BasketFlower> baskets;
    private Storage storage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Soil getSoil() {
        return soil;
    }

    public void setSoil(Soil soil) {
        this.soil = soil;
    }

    public int getWatering() {
        return watering;
    }

    public void setWatering(int watering) {
        this.watering = watering;
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getFlowerImage() {
        return flowerImage;
    }

    public void setFlowerImage(String flowerImage) {
        this.flowerImage = flowerImage;
    }

    public FlowerType getFlowerType() {
        return flowerType;
    }

    public void setFlowerType(FlowerType flowerType) {
        this.flowerType = flowerType;
    }

    public List<OrderFlower> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderFlower> orders) {
        this.orders = orders;
    }

    public List<BasketFlower> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<BasketFlower> baskets) {
        this.baskets = baskets;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
