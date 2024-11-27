package com.github.matheuswwwp.dinenow.model.dish;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    private Float price;
    private String name;
    private String description;
    private Boolean active;

    public Dish() {}

    public Dish(UUID id, Float price, String name, String description) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id) && Objects.equals(price, dish.price) && Objects.equals(name, dish.name) && Objects.equals(description, dish.description) && Objects.equals(active, dish.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name, description, active);
    }
}
