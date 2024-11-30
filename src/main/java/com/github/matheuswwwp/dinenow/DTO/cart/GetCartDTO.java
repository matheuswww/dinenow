package com.github.matheuswwwp.dinenow.DTO.cart;

import java.util.List;
import java.util.Objects;

public class GetCartDTO {
    private String cart_id;
    private Integer quantity;
    private Float cartPrice;
    private Float price;
    private String name;
    private String description;
    private List<String> images;

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(Float cartPrice) {
        this.cartPrice = cartPrice;
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
        GetCartDTO that = (GetCartDTO) o;
        return Objects.equals(getCart_id(), that.getCart_id()) && Objects.equals(getQuantity(), that.getQuantity()) && Objects.equals(getCartPrice(), that.getCartPrice()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getImages(), that.getImages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCart_id(), getQuantity(), getCartPrice(), getPrice(), getName(), getDescription(), getImages());
    }
}
