package com.github.matheuswwwp.dinenow.DTO.order;

import com.github.matheuswwwp.dinenow.DTO.order.util.GetOrder;

import java.util.List;
import java.util.Objects;

public class GetOrderDTO {
    private List<GetOrder> order;
    private Boolean newItem;

    public List<GetOrder> getOrder() {
        return order;
    }

    public void setOrder(List<GetOrder> order) {
        this.order = order;
    }

    public Boolean getNewItem() {
        return newItem;
    }

    public void setNewItem(Boolean newItem) {
        this.newItem = newItem;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GetOrderDTO that = (GetOrderDTO) o;
        return Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getNewItem(), that.getNewItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrder(), getNewItem());
    }
}