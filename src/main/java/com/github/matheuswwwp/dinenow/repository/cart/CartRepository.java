package com.github.matheuswwwp.dinenow.repository.cart;

import com.github.matheuswwwp.dinenow.model.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {}