package by.panasenko.webproject.entity;

import java.math.BigDecimal;
import java.util.List;

public class Basket {
    private int id;
    private BigDecimal totalCost;
    private User user;
    private List<BasketFlower> flowers;

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

    public List<BasketFlower> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<BasketFlower> flowers) {
        this.flowers = flowers;
    }
}
