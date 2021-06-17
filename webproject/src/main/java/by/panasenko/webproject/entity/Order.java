package by.panasenko.webproject.entity;

import java.math.BigDecimal;
import java.util.Date;

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

    public Order() {
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

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (cash != order.cash) return false;
        if (statusOrder != order.statusOrder) return false;
        if (dateDelivery != null ? !dateDelivery.equals(order.dateDelivery) : order.dateDelivery != null) return false;
        if (totalCost != null ? !totalCost.equals(order.totalCost) : order.totalCost != null) return false;
        if (timeOrder != null ? !timeOrder.equals(order.timeOrder) : order.timeOrder != null) return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        if (dateOrder != null ? !dateOrder.equals(order.dateOrder) : order.dateOrder != null) return false;
        return user != null ? user.equals(order.user) : order.user == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (statusOrder != null ? statusOrder.hashCode() : 0);
        result = 31 * result + (dateDelivery != null ? dateDelivery.hashCode() : 0);
        result = 31 * result + (totalCost != null ? totalCost.hashCode() : 0);
        result = 31 * result + (timeOrder != null ? timeOrder.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (dateOrder != null ? dateOrder.hashCode() : 0);
        result = 31 * result + (cash ? 1 : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", statusOrder=").append(statusOrder);
        sb.append(", dateDelivery=").append(dateDelivery);
        sb.append(", totalCost=").append(totalCost);
        sb.append(", timeOrder='").append(timeOrder).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", dateOrder=").append(dateOrder);
        sb.append(", cash=").append(cash);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
