package com.github.matheuswwwp.dinenow.service.cart;

import com.github.matheuswwwp.dinenow.DTO.cart.GetCartDTO;
import com.github.matheuswwwp.dinenow.DTO.dish.GetDishDTO;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.exception.customException.dish.PaginationExceeded;
import com.github.matheuswwwp.dinenow.conf.mapper.Mapper;
import com.github.matheuswwwp.dinenow.model.cart.Cart;
import com.github.matheuswwwp.dinenow.repository.cart.CartRepository;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import com.github.matheuswwwp.dinenow.repository.user.UserRepository;
import com.github.matheuswwwp.dinenow.service.dish.util.GetDishImages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
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
    @Autowired
    private GetDishImages getDishImages;

    public ResponseEntity<?> CreateCart(Integer quantity, String user_id, String dish_id) {
        try {
            var dish_uuid = UUID.fromString(dish_id);
            var user_uuid = UUID.fromString(user_id);
            var dishRepo = dishRepository.findById(dish_uuid);
            var userRepo = userRepository.findById(user_uuid);
            if(dishRepo.isEmpty() || !dishRepo.get().getActive()) {
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

    public ResponseEntity<?> GetCart(String user_id, int pages, int items) {
        try {
            if (pages > 2 || items > 10) {
                throw new PaginationExceeded();
            }
            var cartRepo = cartRepository.findAllByUserId(UUID.fromString(user_id), PageRequest.of(pages, items));
            if (cartRepo.isEmpty()) {
                return new ResponseEntity<>(new RestResponse("nenhum item foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
            }
            ArrayList<GetCartDTO> cartsDTO = new ArrayList<>();
            for(Cart cart: cartRepo) {
                var dishRepo = dishRepository.findById(cart.getDish().getId());
                if(dishRepo.isEmpty()) {
                    return new ResponseEntity<>(new RestResponse("nenhum prato foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
                }
                var dishDTO = Mapper.parseListObjects(dishRepo.stream().toList(), GetDishDTO.class);
                getDishImages.GetDishImages(dishDTO);
                var cartDTO = Mapper.parseObject(dishDTO.get(0), GetCartDTO.class);
                cartDTO.setCartPrice(cart.getPrice());
                cartDTO.setQuantity(cart.getQuantity());
                cartDTO.setCart_id(cart.getId().toString());
                cartsDTO.add(cartDTO);
            }
            logger.info("GetCart - dish get with success");
            return ResponseEntity.status(HttpStatus.OK).body(cartsDTO);
        } catch (PaginationExceeded e) {
            logger.error("GetCart - error trying GetCart: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("limite de páginas ou items excedidos", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        }  catch (IllegalArgumentException e) {
            logger.error("GetCart - error trying GetCart: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("id inválido", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("GetCart - error trying GetCart: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> DeleteCart(String user_id, String cart_id) {
        try {
            var user_uuid = UUID.fromString(user_id);
            var cart_uuid = UUID.fromString(cart_id);
            var cartRepo = cartRepository.findByIdAndUserId(cart_uuid, user_uuid);
            if(cartRepo.isEmpty()) {
                return new ResponseEntity<>(new RestResponse("nenhum item foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
            }
            cartRepository.deleteById(cart_uuid);
            logger.info("DeleteCart - success");
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (IllegalArgumentException e) {
            logger.error("DeleteCart - error trying DeleteCart: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("id inválido", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("DeleteCart - error trying DeleteCart: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> UpdateCart(String user_id, String cart_id, Integer quantity) {
        try {
            var user_uuid = UUID.fromString(user_id);
            var cart_uuid = UUID.fromString(cart_id);
            var cartRepo = cartRepository.findByIdAndUserId(cart_uuid, user_uuid);
            if(cartRepo.isEmpty()) {
                return new ResponseEntity<>(new RestResponse("nenhum item foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
            }
            cartRepository.updateQuantity(cart_uuid, user_uuid, quantity);
            logger.info("UpdateCart - success");
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (IllegalArgumentException e) {
            logger.error("UpdateCart - error trying UpdateCart: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("id inválido", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("UpdateCart - error trying UpdateCart: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}