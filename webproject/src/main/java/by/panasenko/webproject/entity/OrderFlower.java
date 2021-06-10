package by.panasenko.webproject.entity;

import java.math.BigDecimal;

public class OrderFlower {
    private int id;
    private int count;
    private BigDecimal subTotal;
    private Order order;
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

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

        OrderFlower that = (OrderFlower) o;

        if (id != that.id) return false;
        if (count != that.count) return false;
        if (subTotal != null ? !subTotal.equals(that.subTotal) : that.subTotal != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        return flower != null ? flower.equals(that.flower) : that.flower == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + count;
        result = 31 * result + (subTotal != null ? subTotal.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (flower != null ? flower.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderFlower{");
        sb.append("id=").append(id);
        sb.append(", count=").append(count);
        sb.append(", subTotal=").append(subTotal);
        sb.append(", order=").append(order);
        sb.append(", flower=").append(flower);
        sb.append('}');
        return sb.toString();
    }
}
