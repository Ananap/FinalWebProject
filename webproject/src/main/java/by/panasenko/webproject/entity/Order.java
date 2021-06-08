package by.panasenko.webproject.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private Status statusOrder;
    private Date dateDelivery;
    private BigDecimal totalCost;
    private String timeOrder;
    private String address;
    private Date dateOrder;
    private boolean cash;
    private User user;
    private List<OrderFlower> orderFlower;

    public Order(Status status, String address, Date dateOrder, Date dateDelivery, String time, BigDecimal totalCost, User user, boolean cash) {
        this.statusOrder = status;
        this.address = address;
        this.dateOrder = dateOrder;
        this.dateDelivery = dateDelivery;
        this.timeOrder = time;
        this.totalCost = totalCost;
        this.user = user;
        this.cash = cash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(Status statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Date getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
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

    public List<OrderFlower> getOrderFlower() {
        return orderFlower;
    }

    public void setOrderFlower(List<OrderFlower> orderFlower) {
        this.orderFlower = orderFlower;
    }

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }
}
