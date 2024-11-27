package com.github.matheuswwwp.dinenow.DTO.dish;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class UpdateDishDTO {
    @NotBlank
    private String id;
    @Max(1000000000)
    private Float   price;
    private Boolean active;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UpdateDishDTO that = (UpdateDishDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(price, that.price) && Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, active);
    }
}
