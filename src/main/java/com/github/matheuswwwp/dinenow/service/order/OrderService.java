package com.github.matheuswwwp.dinenow.service.order;

import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.exception.customException.order.PriceNotExcepted;
import com.github.matheuswwwp.dinenow.model.order.Order;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import com.github.matheuswwwp.dinenow.repository.order.OrderRepository;
import com.github.matheuswwwp.dinenow.repository.user.UserRepository;
import com.github.matheuswwwp.dinenow.service.order.util.CalcFreight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public ResponseEntity<?> CreateOrder(Order order, String dish_id, String user_id) {
        try {
            var dish_uuid = UUID.fromString(dish_id);
            var user_uuid = UUID.fromString(user_id);
            var dishRepo = dishRepository.findById(dish_uuid);
            var userRepo = userRepository.findById(user_uuid);
            if(dishRepo.isEmpty() || !dishRepo.get().getActive()) {
                return new ResponseEntity<>(new RestResponse("nenhum prato foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
            }
            order.setUser(userRepo.get());
            order.setDish(dishRepo.get());
            var freight = new CalcFreight().CalcFreight() * 100;
            var price = dishRepo.get().getPrice() * 100;
            if(freight + price != order.getPrice() + order.getFreight()) {
                throw new PriceNotExcepted();
            }
            orderRepository.save(order);
            logger.info("CreateOrder - success");
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (PriceNotExcepted e) {
            logger.error("CreateOrder - error trying CreateOrder: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("preço calculado não é igual ao esperado", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            logger.error("CreateOrder - error trying CreateOrder: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("id inválido", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("CreateOrder - error trying CreateOrder: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
