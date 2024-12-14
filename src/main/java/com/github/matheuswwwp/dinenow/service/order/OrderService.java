package com.github.matheuswwwp.dinenow.service.order;

import com.github.matheuswwwp.dinenow.DTO.order.CreateOrderDTO;
import com.github.matheuswwwp.dinenow.DTO.order.CreateDishesDTO;
import com.github.matheuswwwp.dinenow.DTO.order.GetOrderDTO;
import com.github.matheuswwwp.dinenow.DTO.order.util.GetOrder;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.exception.customException.dish.PaginationExceeded;
import com.github.matheuswwwp.dinenow.conf.exception.customException.order.PriceNotExcepted;
import com.github.matheuswwwp.dinenow.conf.mapper.Mapper;
import com.github.matheuswwwp.dinenow.model.dishes.OrderDish;
import com.github.matheuswwwp.dinenow.model.order.Order;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import com.github.matheuswwwp.dinenow.repository.order.OrderRepository;
import com.github.matheuswwwp.dinenow.repository.orderAndOrderDishRepository.OrderAndOrderDishRepository;
import com.github.matheuswwwp.dinenow.repository.orderDishRepository.OrderDishRepository;
import com.github.matheuswwwp.dinenow.repository.user.UserRepository;
import com.github.matheuswwwp.dinenow.service.dish.util.GetDishImages;
import com.github.matheuswwwp.dinenow.service.order.util.CalcFreight;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private OrderDishRepository orderDishRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderAndOrderDishRepository orderAndOrderDishRepository;
    @Autowired
    private GetDishImages getDishImages;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public ResponseEntity<?> UpdateStatus(String status, String order_id) {
        try {
            var order_uuid = UUID.fromString(order_id);
            var orderRepo = orderRepository.findById(order_uuid);
            if(orderRepo.isEmpty()) {
                return new ResponseEntity<>(new RestResponse("nenhum pedido foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
            }
            orderRepository.updateStatus(order_uuid, status);
            logger.info("UpdateStatus - success");
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (IllegalArgumentException e) {
            logger.error("UpdateStatus - error trying UpdateStatus: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("id inválido", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("UpdateStatus - error trying UpdateStatus: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> CreateOrder(CreateOrderDTO createOrderDTO, String user_id) {
        try {
            var freight = new CalcFreight().CalcFreight() * 100;
            var user_uuid = UUID.fromString(user_id);
            var userRepo = userRepository.findById(user_uuid);
            var order = Mapper.parseObject(createOrderDTO, Order.class);
            order.setUser(userRepo.get());
            order.setFreight(freight);
            order = orderRepository.save(order);
            var totalPriceExpected = freight;
            for (CreateDishesDTO dish: createOrderDTO.getDishes()) {
                var dish_uuid = UUID.fromString(dish.getDish_id().toString());
                var dishRepo = dishRepository.findById(dish_uuid);
                if(dishRepo.isEmpty() || !dishRepo.get().getActive()) {
                    return new ResponseEntity<>(new RestResponse("nenhum prato foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
                }
                totalPriceExpected += dishRepo.get().getPrice() * 100 * dish.getQuantity();
                var orderDish = new OrderDish(dishRepo.get().getDish_id(), order, dishRepo.get().getDescription(), (int) (dishRepo.get().getPrice() * 100), dishRepo.get().getName(), dish.getQuantity());
                orderDishRepository.save(orderDish);
            }
            if(totalPriceExpected != createOrderDTO.getTotal_price()) {
                throw new PriceNotExcepted();
            }
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

    public ResponseEntity<?> GetOrder(String filter, int pages, int items, Boolean newItem) {
        try {
            if (pages > 2 || items > 10) {
                throw new PaginationExceeded();
            }
            var res = orderAndOrderDishRepository.findAllOrdersWithDishesByStatus(filter, PageRequest.of(pages, items));
            if(res.isEmpty()) {
                return new ResponseEntity<>(new RestResponse("nenhum pedido foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
            }
            var getOrder = Mapper.parseListObjects(res.toList(), GetOrder.class);
            for (GetOrder order: getOrder) {
                getDishImages.GetDishImages(order.getDishes());
            }
            logger.info("GetOrder - order get with success");
            var getOrderDTO = new GetOrderDTO();
            getOrderDTO.setOrder(getOrder);
            getOrderDTO.setNewItem(newItem);
            return ResponseEntity.status(HttpStatus.OK).body(getOrderDTO);
        } catch (PaginationExceeded e) {
            logger.error("GetOrder - error trying GetOrder: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("limite de páginas ou items excedidos", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("GetOrder - error trying GetOrder: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> GetOrderByUserId(String user_id, int pages, int items) {
        try {
            if (pages > 2 || items > 10) {
                throw new PaginationExceeded();
            }
            var user_uuid = UUID.fromString(user_id);
            var res = orderAndOrderDishRepository.findAllOrdersWithDishesByUserId(user_uuid, PageRequest.of(pages, items));
            if(res.isEmpty()) {
                return new ResponseEntity<>(new RestResponse("nenhum pedido foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
            }
            var getOrderDTO = Mapper.parseListObjects(res.toList(), GetOrder.class);
            for (GetOrder order: getOrderDTO) {
                getDishImages.GetDishImages(order.getDishes());
            }
            logger.info("GetOrder - order get with success");
            return ResponseEntity.status(HttpStatus.OK).body(getOrderDTO);
        } catch (PaginationExceeded e) {
            logger.error("GetOrder - error trying GetOrder: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("limite de páginas ou items excedidos", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("GetOrder - error trying GetOrder: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
