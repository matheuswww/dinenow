package com.github.matheuswwwp.dinenow.DTO.order;

import com.github.matheuswwwp.dinenow.DTO.order.util.GetOrder;

import java.util.List;
import java.util.Objects;

public class GetOrderByUserIdDTO {
    private List<GetOrder> orders;
    private Integer totalPage;

    public GetOrderByUserIdDTO() {}

    public List<GetOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<GetOrder> orders) {
        this.orders = orders;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GetOrderByUserIdDTO that = (GetOrderByUserIdDTO) o;
        return Objects.equals(getOrders(), that.getOrders()) && Objects.equals(getTotalPage(), that.getTotalPage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrders(), getTotalPage());
    }
}
