package com.github.matheuswwwp.dinenow.DTO.order;

import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Objects;

public class CreateOrderDTO {
    @NotNull
    @Min(1)
    @Max(1000000000)
    private Integer total_price;
    @NotNull
    @Min(1)
    @Max(1000000000)
    private Integer number;
    @NotBlank
    @Size(min = 2, max = 160)
    private String neighborhood;
    @NotBlank
    @Size(min = 2, max = 160)
    private String street;
    @NotBlank
    @Size(min = 2, max = 150)
    private String obs;
    @NotBlank
    @Size(min = 2, max = 40)
    private String complement;
    @NotNull
    private List<CreateDishesDTO> dishes;

    public Integer getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Integer total_price) {
        this.total_price = total_price;
    }

    public List<CreateDishesDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<CreateDishesDTO> dishes) {
        this.dishes = dishes;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreateOrderDTO that = (CreateOrderDTO) o;
        return Objects.equals(getTotal_price(), that.getTotal_price()) && Objects.equals(getNumber(), that.getNumber()) && Objects.equals(getNeighborhood(), that.getNeighborhood()) && Objects.equals(getStreet(), that.getStreet()) && Objects.equals(getObs(), that.getObs()) && Objects.equals(getComplement(), that.getComplement()) && Objects.equals(getDishes(), that.getDishes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTotal_price(), getNumber(), getNeighborhood(), getStreet(), getObs(), getComplement(), getDishes());
    }
}
