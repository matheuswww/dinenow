package com.github.matheuswwwp.dinenow.repository.dish;

import com.github.matheuswwwp.dinenow.model.dish.Dish;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DishRepository extends JpaRepository<Dish, UUID> {
    Page<Dish> findAll(Pageable pageable);
    @Modifying
    @Transactional
    @Query("UPDATE Dish d SET d.price = COALESCE(:newPrice, d.price), d.active = COALESCE(:active, d.active) WHERE d.id = :id")
    int updateDish(@Param("id") UUID id, @Param("newPrice") Float newPrice, @Param("active") Boolean active);
}