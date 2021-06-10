package by.panasenko.webproject.entity;

import java.math.BigDecimal;

public class Basket {
    private int id;
    private BigDecimal totalCost;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Basket basket = (Basket) o;

        if (id != basket.id) return false;
        if (totalCost != null ? !totalCost.equals(basket.totalCost) : basket.totalCost != null) return false;
        return user != null ? user.equals(basket.user) : basket.user == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (totalCost != null ? totalCost.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Basket{");
        sb.append("id=").append(id);
        sb.append(", totalCost=").append(totalCost);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
