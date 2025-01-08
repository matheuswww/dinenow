package com.github.matheuswwwp.dinenow.DTO.dish;

import java.util.List;
import java.util.Objects;

public class DishDTO {
    private String dish_id;
    private Float price;
    private String name;
    private String description;
    private List<String> images;

    public DishDTO(){}

    public DishDTO(String dish_id, String description, String name, Float price) {
        this.dish_id = dish_id;
        this.description = description;
        this.name = name;
        this.price = price;
    }

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        DishDTO that = (DishDTO) o;
        return Objects.equals(getDish_id(), that.getDish_id()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getImages(), that.getImages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDish_id(), getPrice(), getName(), getDescription(), getImages());
    }
}
