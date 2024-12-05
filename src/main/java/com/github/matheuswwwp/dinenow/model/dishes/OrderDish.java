package com.github.matheuswwwp.dinenow.model.dishes;

import com.github.matheuswwwp.dinenow.model.order.Order;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "client_order_dish")
public class OrderDish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID dish_id;
    private Integer price;
    private String name;
    private String description;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderDish(){}

    public OrderDish(UUID dish_id, Order order, String description, Integer price, String name, Integer quantity) {
        this.dish_id = dish_id;
        this.order = order;
        this.description = description;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
    }

    public UUID getDish_id() {
        return dish_id;
    }

    public void setDish_id(UUID dish_id) {
        this.dish_id = dish_id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderDish orderDish = (OrderDish) o;
        return Objects.equals(getDish_id(), orderDish.getDish_id()) && Objects.equals(getPrice(), orderDish.getPrice()) && Objects.equals(getName(), orderDish.getName()) && Objects.equals(getDescription(), orderDish.getDescription()) && Objects.equals(getQuantity(), orderDish.getQuantity()) && Objects.equals(getOrder(), orderDish.getOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDish_id(), getPrice(), getName(), getDescription(), getQuantity(), getOrder());
    }
}
