package orderTest;

import com.github.matheuswwwp.dinenow.model.dish.Dish;
import com.github.matheuswwwp.dinenow.model.order.Order;
import com.github.matheuswwwp.dinenow.model.user.User;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import com.github.matheuswwwp.dinenow.repository.order.OrderRepository;
import com.github.matheuswwwp.dinenow.repository.user.UserRepository;
import com.github.matheuswwwp.dinenow.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

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

    @Test
    public void Success() {
        var user_id = UUID.randomUUID();
        var dish_id = UUID.randomUUID();
        var order = new Order();
        var dish = new Dish();
        var user = new User();
        dish.setActive(true);
        dish.setPrice(10F);
        order.setPrice(1000);
        order.setFreight(1000);
        Mockito.when(dishRepository.findById(dish_id)).thenReturn(Optional.of(dish));
        Mockito.when(userRepository.findById(user_id)).thenReturn(Optional.of(user));
        var res = service.CreateOrder(order, dish_id.toString(), user_id.toString());
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
    }

    @Test
    public void PriceNotExcepted() {
        var user_id = UUID.randomUUID();
        var dish_id = UUID.randomUUID();
        var order = new Order();
        var dish = new Dish();
        var user = new User();
        dish.setActive(true);
        dish.setPrice(100F);
        order.setPrice(1000);
        order.setFreight(100);
        Mockito.when(dishRepository.findById(dish_id)).thenReturn(Optional.of(dish));
        Mockito.when(userRepository.findById(user_id)).thenReturn(Optional.of(user));
        var res = service.CreateOrder(order, dish_id.toString(), user_id.toString());
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    public void DishNotFound() {
        var user_id = UUID.randomUUID();
        var dish_id = UUID.randomUUID();
        var order = new Order();
        Mockito.when(dishRepository.findById(dish_id)).thenReturn(Optional.empty());
        var res = service.CreateOrder(order, dish_id.toString(), user_id.toString());
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

    @Test
    public void DishNotActive() {
        var user_id = UUID.randomUUID();
        var dish_id = UUID.randomUUID();
        var dish = new Dish();
        var order = new Order();
        dish.setActive(false);
        Mockito.when(dishRepository.findById(dish_id)).thenReturn(Optional.of(dish));
        var res = service.CreateOrder(order, dish_id.toString(), user_id.toString());
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

    @Test
    public void InvalidIdTest() {
        var order = new Order();
        var res = service.CreateOrder(order,"test", "test");
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }
}
