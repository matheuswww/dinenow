package com.github.matheuswwwp.dinenow.DTO.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class UpdateStatusDTO {
    @NotBlank
    @Size(min = 36, max = 36)
    private String id;
    @NotBlank
    @Pattern(regexp = "waiting|preparing|route|finished", message = "status deve ser: waiting, preparing, route ou finished")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UpdateStatusDTO that = (UpdateStatusDTO) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStatus());
    }
}
