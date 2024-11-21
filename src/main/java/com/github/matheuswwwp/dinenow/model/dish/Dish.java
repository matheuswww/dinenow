package com.github.matheuswwwp.dinenow.model.dish;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Float price;
    private String name;
    private String description;

    public Dish() {}

    public Dish(String id, Float price, String name, String description) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dish dine = (Dish) o;
        return Objects.equals(id, dine.id) && Objects.equals(price, dine.price) && Objects.equals(name, dine.name) && Objects.equals(description, dine.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name, description);
    }
}
