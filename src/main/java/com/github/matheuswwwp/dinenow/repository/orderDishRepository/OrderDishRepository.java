package com.github.matheuswwwp.dinenow.repository.orderDishRepository;

import com.github.matheuswwwp.dinenow.model.dishes.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderDishRepository extends JpaRepository<OrderDish, UUID> {}

