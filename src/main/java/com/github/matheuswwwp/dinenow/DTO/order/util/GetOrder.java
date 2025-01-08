package com.github.matheuswwwp.dinenow.DTO.order.util;

import com.github.matheuswwwp.dinenow.DTO.dish.DishDTO;
import com.github.matheuswwwp.dinenow.DTO.dish.GetDishDTO;

import java.util.List;
import java.util.Objects;

public class GetOrder {
    private String id;
    private Integer number;
    private String neighborhood;
    private String street;
    private String obs;
    private String complement;
    private Float total_price;
    private Float freight;
    private List<DishDTO> dishes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
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

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Float total_price) {
        this.total_price = total_price;
    }

    public Float getFreight() {
        return freight;
    }

    public void setFreight(Float freight) {
        this.freight = freight;
    }

    public List<DishDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishDTO> dishes) {
        this.dishes = dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GetOrder that = (GetOrder) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNumber(), that.getNumber()) && Objects.equals(getNeighborhood(), that.getNeighborhood()) && Objects.equals(getStreet(), that.getStreet()) && Objects.equals(getObs(), that.getObs()) && Objects.equals(getComplement(), that.getComplement()) && Objects.equals(getTotal_price(), that.getTotal_price()) && Objects.equals(getFreight(), that.getFreight()) && Objects.equals(getDishes(), that.getDishes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getNeighborhood(), getStreet(), getObs(), getComplement(), getTotal_price(), getFreight(), getDishes());
    }
}
