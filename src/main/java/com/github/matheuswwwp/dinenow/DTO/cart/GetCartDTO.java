package com.github.matheuswwwp.dinenow.DTO.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetCartDTO {
    private List<CartDTO> carts;
    private Integer totalPages;

    public GetCartDTO() {}

    public List<CartDTO> getCarts() {
        return carts;
    }

    public void setCarts(ArrayList<CartDTO> carts) {
        this.carts = carts;
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
        GetCartDTO that = (GetCartDTO) o;
        return Objects.equals(carts, that.carts) && Objects.equals(totalPages, that.totalPages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carts, totalPages);
    }
}
