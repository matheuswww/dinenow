package com.github.matheuswwwp.dinenow.DTO.dish;


import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class DishDTO {
    @Min(1)
    @Max(1000000000)
    private Float price;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 2, max = 200)
    private String description;

    @NotNull
    private MultipartFile[] file;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DishDTO dishDTO = (DishDTO) o;
        return Objects.equals(price, dishDTO.price) &&
                Objects.equals(name, dishDTO.name) &&
                Objects.equals(description, dishDTO.description) &&
                Objects.equals(file, dishDTO.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, name, description, file);
    }
}