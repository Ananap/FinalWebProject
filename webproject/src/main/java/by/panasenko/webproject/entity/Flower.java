package by.panasenko.webproject.entity;

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
    private Storage storage;

    public Flower(String name, String description, double price, Soil soil, int watering, boolean light, String originCountry, FlowerType flowerType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.soil = soil;
        this.watering = watering;
        this.light = light;
        this.originCountry = originCountry;
        this.flowerType = flowerType;
    }

    public Flower() {
    }

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

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flower flower = (Flower) o;

        if (id != flower.id) return false;
        if (Double.compare(flower.price, price) != 0) return false;
        if (watering != flower.watering) return false;
        if (light != flower.light) return false;
        if (name != null ? !name.equals(flower.name) : flower.name != null) return false;
        if (description != null ? !description.equals(flower.description) : flower.description != null) return false;
        if (soil != null ? !soil.equals(flower.soil) : flower.soil != null) return false;
        if (originCountry != null ? !originCountry.equals(flower.originCountry) : flower.originCountry != null)
            return false;
        if (flowerImage != null ? !flowerImage.equals(flower.flowerImage) : flower.flowerImage != null) return false;
        if (flowerType != null ? !flowerType.equals(flower.flowerType) : flower.flowerType != null) return false;
        return storage != null ? storage.equals(flower.storage) : flower.storage == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (soil != null ? soil.hashCode() : 0);
        result = 31 * result + watering;
        result = 31 * result + (light ? 1 : 0);
        result = 31 * result + (originCountry != null ? originCountry.hashCode() : 0);
        result = 31 * result + (flowerImage != null ? flowerImage.hashCode() : 0);
        result = 31 * result + (flowerType != null ? flowerType.hashCode() : 0);
        result = 31 * result + (storage != null ? storage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Flower{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", soil=").append(soil);
        sb.append(", watering=").append(watering);
        sb.append(", light=").append(light);
        sb.append(", originCountry='").append(originCountry).append('\'');
        sb.append(", flowerImage='").append(flowerImage).append('\'');
        sb.append(", flowerType=").append(flowerType);
        sb.append(", storage=").append(storage);
        sb.append('}');
        return sb.toString();
    }
}
