package com.github.matheuswwwp.dinenow.DTO.order;

import java.util.Objects;

public class GetOrderReqDTO {
    private int pages;
    private int items;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GetOrderReqDTO that = (GetOrderReqDTO) o;
        return getPages() == that.getPages() && getItems() == that.getItems();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPages(), getItems());
    }
}
