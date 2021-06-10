package by.panasenko.webproject.entity;

public final class Soil {
    private int soilId;
    private String name;
    private String description;

    public Soil(String description) {
        this.description = description;
    }

    public int getSoilId() {
        return soilId;
    }

    public void setSoilId(int soilId) {
        this.soilId = soilId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Soil soil = (Soil) o;

        if (soilId != soil.soilId) return false;
        if (name != null ? !name.equals(soil.name) : soil.name != null) return false;
        return description != null ? description.equals(soil.description) : soil.description == null;
    }

    @Override
    public int hashCode() {
        int result = soilId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Soil{");
        sb.append("soilId=").append(soilId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
