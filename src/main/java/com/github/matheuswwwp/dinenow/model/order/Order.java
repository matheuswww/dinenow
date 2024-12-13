package com.github.matheuswwwp.dinenow.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.matheuswwwp.dinenow.model.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "client_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    private Integer total_price;
    private Integer freight;
    private String street;
    private Integer number;
    private String neighborhood;
    private String complement;
    private String obs;
    private String status = "waiting";
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Order(){}

    public Order(Integer total_price, Integer freight, String street, Integer number, String neighborhood, String complement, String obs, User user, String status) {
        this.total_price = total_price;
        this.freight = freight;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.obs = obs;
        this.user = user;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Integer getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Integer total_price) {
        this.total_price = total_price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) && Objects.equals(getTotal_price(), order.getTotal_price()) && Objects.equals(getFreight(), order.getFreight()) && Objects.equals(getStreet(), order.getStreet()) && Objects.equals(getNumber(), order.getNumber()) && Objects.equals(getNeighborhood(), order.getNeighborhood()) && Objects.equals(getComplement(), order.getComplement()) && Objects.equals(getObs(), order.getObs()) && Objects.equals(getStatus(), order.getStatus()) && Objects.equals(getUser(), order.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTotal_price(), getFreight(), getStreet(), getNumber(), getNeighborhood(), getComplement(), getObs(), getStatus(), getUser());
    }
}
