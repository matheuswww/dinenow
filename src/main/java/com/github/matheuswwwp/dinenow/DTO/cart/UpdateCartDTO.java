package com.github.matheuswwwp.dinenow.DTO.cart;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class UpdateCartDTO {
    @NotNull
    @Min(1)
    @Max(1000000000)
    private Integer quantity;
    @NotBlank
    @Size(min = 2, max = 50)
    private String cartId;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UpdateCartDTO that = (UpdateCartDTO) o;
        return Objects.equals(getQuantity(), that.getQuantity()) && Objects.equals(getCartId(), that.getCartId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity(), getCartId());
    }
}
