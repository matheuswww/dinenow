package orderTest;

import com.github.matheuswwwp.dinenow.DTO.order.CreateDishesDTO;
import com.github.matheuswwwp.dinenow.DTO.order.CreateOrderDTO;
import com.github.matheuswwwp.dinenow.model.dish.Dish;
import com.github.matheuswwwp.dinenow.model.dishes.OrderDish;
import com.github.matheuswwwp.dinenow.model.order.Order;
import com.github.matheuswwwp.dinenow.model.orderAndOrderDIsh.OrderAndOrderDish;
import com.github.matheuswwwp.dinenow.model.user.User;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import com.github.matheuswwwp.dinenow.repository.order.OrderRepository;
import com.github.matheuswwwp.dinenow.repository.orderAndOrderDishRepository.OrderAndOrderDishRepository;
import com.github.matheuswwwp.dinenow.repository.orderDishRepository.OrderDishRepository;
import com.github.matheuswwwp.dinenow.repository.user.UserRepository;
import com.github.matheuswwwp.dinenow.service.dish.util.GetDishImages;
import com.github.matheuswwwp.dinenow.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GetOrderByUserIdTest {
    @InjectMocks
    private OrderService service;
    @Mock
    private OrderAndOrderDishRepository orderAndOrderDishRepository;
    @Mock
    private GetDishImages getDishImages;

    @Test
    public void Success() {
        List<OrderAndOrderDish> mockOrderAndOrderDish = new ArrayList<>();
        var orderAndOrderDish = new OrderAndOrderDish();
        var user_id = UUID.randomUUID();

        mockOrderAndOrderDish.add(orderAndOrderDish);
        Page<OrderAndOrderDish> mockPage = new PageImpl<>(mockOrderAndOrderDish);
        Mockito.when(orderAndOrderDishRepository.findAllOrdersWithDishesByUserId(user_id, PageRequest.of(0, 10))).thenReturn(mockPage);
        var res = service.GetOrderByUserId(user_id.toString(), 0, 10);
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    public void pageAndItemsExceeded() {
        var user_id = UUID.randomUUID();
        var res = service.GetOrderByUserId(user_id.toString(), 50, 50);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    public void notFound() {
        var user_id = UUID.randomUUID();
        Page<OrderAndOrderDish> emptyPage = new PageImpl<>(Collections.emptyList());
        Mockito.when(orderAndOrderDishRepository.findAllOrdersWithDishesByUserId(user_id, PageRequest.of(0, 10))).thenReturn(emptyPage);
        var res = service.GetOrderByUserId(user_id.toString(), 0, 10);
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }
}
