package com.github.matheuswwwp.dinenow.DTO.cart;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class CreateCartDTO {
    @NotNull
    @Min(1)
    @Max(1000000000)
    private Integer quantity;
    @NotBlank
    @Size(min = 2, max = 50)
    private String dishId;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreateCartDTO that = (CreateCartDTO) o;
        return Objects.equals(getQuantity(), that.getQuantity()) && Objects.equals(getDishId(), that.getDishId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity(), getDishId());
    }
}
