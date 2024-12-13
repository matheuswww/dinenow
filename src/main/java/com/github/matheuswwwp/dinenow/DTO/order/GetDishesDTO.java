package com.github.matheuswwwp.dinenow.DTO.order;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GetDishesDTO {
    private UUID dish_id;
    private Integer price;
    private String name;
    private String description;
    private List<String> images;

    public UUID getDish_id() {
        return dish_id;
    }

    public void setDish_id(UUID dish_id) {
        this.dish_id = dish_id;
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
        GetDishesDTO that = (GetDishesDTO) o;
        return Objects.equals(getDish_id(), that.getDish_id()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getImages(), that.getImages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDish_id(), getPrice(), getName(), getDescription(), getImages());
    }
}
