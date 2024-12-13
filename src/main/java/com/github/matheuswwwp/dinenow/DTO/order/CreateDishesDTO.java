package com.github.matheuswwwp.dinenow.DTO.order;

import jakarta.validation.constraints.*;
import java.util.Objects;

public class CreateDishesDTO {
    @NotBlank
    @Size(min = 2, max = 36)
    private String dish_id;
    @NotNull
    @Min(1)
    @Max(1000000000)
    private Integer quantity;

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreateDishesDTO dishes = (CreateDishesDTO) o;
        return Objects.equals(getDish_id(), dishes.getDish_id()) && Objects.equals(getQuantity(), dishes.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDish_id(), getQuantity());
    }
}
