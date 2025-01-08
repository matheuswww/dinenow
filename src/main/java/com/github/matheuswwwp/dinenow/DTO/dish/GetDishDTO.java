package com.github.matheuswwwp.dinenow.DTO.dish;

import java.util.List;
import java.util.Objects;

public class GetDishDTO {
    private List<DishDTO> dishes;
    private Integer totalPages;

    public GetDishDTO() {}

    public List<DishDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishDTO> dishes) {
        this.dishes = dishes;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GetDishDTO that = (GetDishDTO) o;
        return Objects.equals(getDishes(), that.getDishes()) && Objects.equals(getTotalPages(), that.getTotalPages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDishes(), getTotalPages());
    }
}
