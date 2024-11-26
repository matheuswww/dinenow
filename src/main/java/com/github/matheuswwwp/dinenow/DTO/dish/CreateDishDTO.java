package com.github.matheuswwwp.dinenow.DTO.dish;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CreateDishDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String>  imgLink;

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

    public MultipartFile[] getFile() { return file; }

    public void setFile(MultipartFile[] file) {
        this.file = file;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public List<String> getImgLink() { return imgLink; }

    public void setImgLink(List<String> imgLink) { this.imgLink = imgLink; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreateDishDTO createDishDTO = (CreateDishDTO) o;
        return Objects.equals(id, createDishDTO.id) && Objects.equals(price, createDishDTO.price) && Objects.equals(name, createDishDTO.name) && Objects.equals(description, createDishDTO.description) && Objects.deepEquals(file, createDishDTO.file) && Objects.equals(imgLink, createDishDTO.imgLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name, description, Arrays.hashCode(file), imgLink);
    }
}