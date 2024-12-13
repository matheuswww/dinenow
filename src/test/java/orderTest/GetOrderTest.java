package orderTest;

import com.github.matheuswwwp.dinenow.model.orderAndOrderDIsh.OrderAndOrderDish;
import com.github.matheuswwwp.dinenow.repository.orderAndOrderDishRepository.OrderAndOrderDishRepository;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GetOrderTest {
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

        mockOrderAndOrderDish.add(orderAndOrderDish);
        Page<OrderAndOrderDish> mockPage = new PageImpl<>(mockOrderAndOrderDish);
        Mockito.when(orderAndOrderDishRepository.findAllOrdersWithDishesByStatus(null, PageRequest.of(0, 10))).thenReturn(mockPage);
        var res = service.GetOrder(null, 0, 10, false);
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    public void pageAndItemsExceeded() {
        var res = service.GetOrder( null, 50, 50, false);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    public void notFound() {
        Page<OrderAndOrderDish> emptyPage = new PageImpl<>(Collections.emptyList());
        Mockito.when(orderAndOrderDishRepository.findAllOrdersWithDishesByStatus(null, PageRequest.of(0, 10))).thenReturn(emptyPage);
        var res = service.GetOrder(null, 0, 10, false);
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

}
