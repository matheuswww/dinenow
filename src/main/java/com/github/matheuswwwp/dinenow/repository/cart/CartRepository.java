package com.github.matheuswwwp.dinenow.repository.cart;

import com.github.matheuswwwp.dinenow.model.cart.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByIdAndUserId(UUID cart_id, UUID user_id);
    Page<Cart> findAllByUserId(UUID user_id, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Cart c SET c.quantity = :quantity WHERE c.id = :id AND c.user.id = :userId")
    int updateQuantity(@Param("id") UUID cartId,
                       @Param("userId") UUID userId,
                       @Param("quantity") Integer quantity);

}