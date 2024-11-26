package com.github.matheuswwwp.dinenow.repository.dish;

import com.github.matheuswwwp.dinenow.model.dish.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DishRepository extends JpaRepository<Dish, UUID> {
    Page<Dish> findAll(Pageable pageable);
}