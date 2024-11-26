package com.github.matheuswwwp.dinenow.DTO.dish;

import java.util.List;
import java.util.Objects;

public class GetAllDishDTO {
    private String id;
    private Float price;
    private String name;
    private String description;
    private List<String> images;

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GetAllDishDTO that = (GetAllDishDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(price, that.price) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(images, that.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name, description, images);
    }
}
