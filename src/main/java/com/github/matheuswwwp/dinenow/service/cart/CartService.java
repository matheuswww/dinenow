package com.github.matheuswwwp.dinenow.service.cart;

import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.model.cart.Cart;
import com.github.matheuswwwp.dinenow.repository.cart.CartRepository;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import com.github.matheuswwwp.dinenow.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> CreateCart(Integer quantity, String user_id, String dish_id) {
        try {
            var dish_uuid = UUID.fromString(dish_id);
            var user_uuid = UUID.fromString(user_id);
            var dishRepo = dishRepository.findById(dish_uuid);
            var userRepo = userRepository.findById(user_uuid);
            if(dishRepo.isEmpty()) {
                return new ResponseEntity<>(new RestResponse("nenhum prato foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
            }
            var price = ((dishRepo.get().getPrice()) * 100) * quantity / 100;
            var cart = new Cart(userRepo.get(), dishRepo.get(), price, quantity);
            cartRepository.save(cart);
            logger.info("CreateCart - success");
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (IllegalArgumentException e) {
            logger.error("CreateCart - error trying CreateCart: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("id inválido", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("CreateCart - error trying CreateCart: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}