package com.github.matheuswwwp.dinenow.DTO.cart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class DeleteCartDTO {
    @NotBlank
    @Size(min = 2, max = 50)
    private String cartId;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DeleteCartDTO that = (DeleteCartDTO) o;
        return Objects.equals(getCartId(), that.getCartId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCartId());
    }
}
