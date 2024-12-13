package com.github.matheuswwwp.dinenow.repository.orderAndOrderDishRepository;

import com.github.matheuswwwp.dinenow.model.orderAndOrderDIsh.OrderAndOrderDish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderAndOrderDishRepository extends JpaRepository<OrderAndOrderDish, UUID>  {
    @Query("SELECT co FROM OrderAndOrderDish co JOIN FETCH co.dishes WHERE co.user.id = :user_id")
    Page<OrderAndOrderDish> findAllOrdersWithDishesByUserId(@Param("user_id") UUID userId, Pageable pageable);
    @Query("SELECT co FROM OrderAndOrderDish co JOIN FETCH co.dishes WHERE ((:filter IS NULL AND co.status IS NULL) OR co.status = :filter)")
    Page<OrderAndOrderDish> findAllOrdersWithDishesByStatus(@Param("filter") String filter, Pageable pageable);
}