package orderTest;

import com.github.matheuswwwp.dinenow.DTO.order.CreateOrderDTO;
import com.github.matheuswwwp.dinenow.DTO.order.Dishes;
import com.github.matheuswwwp.dinenow.model.dish.Dish;
import com.github.matheuswwwp.dinenow.model.dishes.OrderDish;
import com.github.matheuswwwp.dinenow.model.order.Order;
import com.github.matheuswwwp.dinenow.model.user.User;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import com.github.matheuswwwp.dinenow.repository.order.OrderRepository;
import com.github.matheuswwwp.dinenow.repository.orderDishRepository.OrderDishRepository;
import com.github.matheuswwwp.dinenow.repository.user.UserRepository;
import com.github.matheuswwwp.dinenow.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class OrderTest {
    @InjectMocks
    private OrderService service;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderDishRepository orderDishRepository;

    @Test
    public void Success() {
        var user_id = UUID.randomUUID().toString();
        var dish_id = UUID.randomUUID().toString();
        var orderDTO = new CreateOrderDTO();
        var dish = new Dish();
        var user = new User();
        dish.setActive(true);
        dish.setPrice(10F);
        orderDTO.setTotal_price(2000);
        var dishes = new Dishes();
        dishes.setDish_id(dish_id.toString());
        dishes.setQuantity(1);
        orderDTO.setDishes(List.of(dishes));
        Mockito.when(dishRepository.findById(UUID.fromString(dish_id))).thenReturn(Optional.of(dish));
        Mockito.when(userRepository.findById(UUID.fromString(user_id))).thenReturn(Optional.of(user));
        var order = new Order();
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);
        Mockito.when(orderDishRepository.save(Mockito.any(OrderDish.class))).thenReturn(new OrderDish());
        var res = service.CreateOrder(orderDTO, user_id);
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
    }

    @Test
    public void PriceNotExcepted() {
        var user_id = UUID.randomUUID().toString();
        var dish_id = UUID.randomUUID().toString();
        var orderDTO = new CreateOrderDTO();
        var dish = new Dish();
        var user = new User();
        dish.setActive(true);
        dish.setPrice(100F);
        var dishes = new Dishes();
        dishes.setDish_id(dish_id.toString());
        dishes.setQuantity(10);
        orderDTO.setDishes(List.of(dishes));
        orderDTO.setTotal_price(10000);

        Mockito.when(dishRepository.findById(UUID.fromString(dish_id))).thenReturn(Optional.of(dish));
        Mockito.when(userRepository.findById(UUID.fromString(user_id))).thenReturn(Optional.of(user));

        var res = service.CreateOrder(orderDTO, user_id);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    public void DishNotFound() {
        var user_id = UUID.randomUUID().toString();
        var dish_id = UUID.randomUUID().toString();
        var orderDTO = new CreateOrderDTO();
        var user = new User();

        var dishes = new Dishes();
        dishes.setDish_id(dish_id.toString());
        orderDTO.setDishes(List.of(dishes));
        Mockito.when(dishRepository.findById(UUID.fromString(dish_id))).thenReturn(Optional.empty());
        Mockito.when(userRepository.findById(UUID.fromString(user_id))).thenReturn(Optional.of(user));

        var res = service.CreateOrder(orderDTO, user_id);
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

    @Test
    public void DishNotActive() {
        var user_id = UUID.randomUUID();
        var dish_id = UUID.randomUUID();
        var orderDTO = new CreateOrderDTO();
        var dish = new Dish();
        var user = new User();

        dish.setActive(false);
        var dishes = new Dishes();
        dishes.setDish_id(dish_id.toString());
        orderDTO.setDishes(List.of(dishes));
        Mockito.when(dishRepository.findById(UUID.fromString(dish_id.toString()))).thenReturn(Optional.of(dish));
        Mockito.when(userRepository.findById(UUID.fromString(user_id.toString()))).thenReturn(Optional.of(user));

        var res = service.CreateOrder(orderDTO, user_id.toString());
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

    @Test
    public void InvalidIdTest() {
        var orderDTO = new CreateOrderDTO();
        var res = service.CreateOrder(orderDTO, "test_invalid");
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }
}
