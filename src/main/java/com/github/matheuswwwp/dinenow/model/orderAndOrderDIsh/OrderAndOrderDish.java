package com.github.matheuswwwp.dinenow.model.orderAndOrderDIsh;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.matheuswwwp.dinenow.model.dishes.OrderDish;
import com.github.matheuswwwp.dinenow.model.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "client_order")
public class OrderAndOrderDish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    private int total_price;
    private int freight;
    private String street;
    private int number;
    private String neighborhood;
    private String obs;
    private String complement;
    private String status;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDish> dishes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<OrderDish> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDish> dishes) {
        this.dishes = dishes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderAndOrderDish that = (OrderAndOrderDish) o;
        return getTotal_price() == that.getTotal_price() && getFreight() == that.getFreight() && getNumber() == that.getNumber() && Objects.equals(getId(), that.getId()) && Objects.equals(getStreet(), that.getStreet()) && Objects.equals(getNeighborhood(), that.getNeighborhood()) && Objects.equals(getObs(), that.getObs()) && Objects.equals(getComplement(), that.getComplement()) && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getUser(), that.getUser()) && Objects.equals(getDishes(), that.getDishes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTotal_price(), getFreight(), getStreet(), getNumber(), getNeighborhood(), getObs(), getComplement(), getStatus(), getUser(), getDishes());
    }
}
