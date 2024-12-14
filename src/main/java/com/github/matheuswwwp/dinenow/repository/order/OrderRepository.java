package com.github.matheuswwwp.dinenow.repository.order;

import com.github.matheuswwwp.dinenow.model.order.Order;
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
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findAll(Pageable pageable);
    @Transactional
    @Modifying
    @Query("UPDATE Order c SET c.status = :status WHERE c.id = :id")
    int updateStatus(@Param("id") UUID id, @Param("status") String status);
}
