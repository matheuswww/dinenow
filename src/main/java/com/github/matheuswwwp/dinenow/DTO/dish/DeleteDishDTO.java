package com.github.matheuswwwp.dinenow.DTO.dish;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class DeleteDishDTO {
    @NotBlank
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DeleteDishDTO that = (DeleteDishDTO) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
