package com.github.matheuswwwp.dinenow.DTO.order;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class CreateOrderDTO {
    @NotNull
    @Min(1)
    @Max(1000000000)
    private Integer price;
    @NotNull
    @Min(1)
    @Max(1000000000)
    private Integer freight;
    @NotNull
    @Min(1)
    @Max(1000000000)
    private Integer quantity;
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
    @NotBlank
    @Size(min = 2, max = 36)
    private String dish_id;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
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
        return Objects.equals(price, that.price) && Objects.equals(freight, that.freight) && Objects.equals(quantity, that.quantity) && Objects.equals(number, that.number) && Objects.equals(neighborhood, that.neighborhood) && Objects.equals(street, that.street) && Objects.equals(obs, that.obs) && Objects.equals(complement, that.complement) && Objects.equals(dish_id, that.dish_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, freight, quantity, number, neighborhood, street, obs, complement, dish_id);
    }
}
