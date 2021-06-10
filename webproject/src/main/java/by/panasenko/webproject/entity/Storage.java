package by.panasenko.webproject.entity;

public class Storage {
    private int id;
    private int count;
    private Flower flower;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Storage storage = (Storage) o;

        if (id != storage.id) return false;
        if (count != storage.count) return false;
        return flower != null ? flower.equals(storage.flower) : storage.flower == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + count;
        result = 31 * result + (flower != null ? flower.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Storage{");
        sb.append("id=").append(id);
        sb.append(", count=").append(count);
        sb.append(", flower=").append(flower);
        sb.append('}');
        return sb.toString();
    }
}
