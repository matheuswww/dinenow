package com.github.matheuswwwp.dinenow.DTO.dish;

import java.util.List;
import java.util.Objects;

public class GetDishDTO {
    private String dish_id;
    private Float price;
    private String name;
    private String description;
    private List<String> images;

    public GetDishDTO(){}

    public GetDishDTO(String dish_id, String description, String name, Float price) {
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GetDishDTO that = (GetDishDTO) o;
        return Objects.equals(dish_id, that.dish_id) && Objects.equals(price, that.price) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(images, that.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dish_id, price, name, description, images);
    }
}
