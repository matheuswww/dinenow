package com.github.matheuswwwp.dinenow.DTO.dish;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Objects;

public class CreateDishDTO {
    @NotNull
    @Min(1)
    @Max(1000000000)
    private Float price;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 200)
    private String description;

    @NotNull
    private MultipartFile[] file;

    @NotNull
    private boolean active;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile[] getFile() {
        return file;
    }

    public void setFile(MultipartFile[] file) {
        this.file = file;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreateDishDTO that = (CreateDishDTO) o;
        return active == that.active && Objects.equals(price, that.price) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.deepEquals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, name, description, Arrays.hashCode(file), active);
    }
}