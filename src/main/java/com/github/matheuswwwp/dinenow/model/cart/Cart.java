package com.github.matheuswwwp.dinenow.model.cart;

import com.github.matheuswwwp.dinenow.model.dish.Dish;
import com.github.matheuswwwp.dinenow.model.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    private Integer quantity;
    private Float price;
    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Cart() {}

    public Cart(User user, Dish dish, Float price, Integer quantity) {
        this.user = user;
        this.dish = dish;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(getId(), cart.getId()) && Objects.equals(getQuantity(), cart.getQuantity()) && Objects.equals(getPrice(), cart.getPrice()) && Objects.equals(getDish(), cart.getDish()) && Objects.equals(getUser(), cart.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuantity(), getPrice(), getDish(), getUser());
    }
}
