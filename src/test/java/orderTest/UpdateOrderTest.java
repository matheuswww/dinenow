package orderTest;

import com.github.matheuswwwp.dinenow.DTO.order.CreateOrderDTO;
import com.github.matheuswwwp.dinenow.model.order.Order;
import com.github.matheuswwwp.dinenow.repository.order.OrderRepository;
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
public class UpdateOrderTest {
    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void Success() {
        var order_id = UUID.randomUUID();
        var order = new Order();
        Mockito.when(orderRepository.findById(order_id)).thenReturn(Optional.of(order));
        Mockito.when(orderRepository.updateStatus(order_id, "preparing")).thenReturn(1);
        var res = service.UpdateStatus("preparing", order_id.toString());
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    public void OrderNotFound() {
        var order_id = UUID.randomUUID();
        Mockito.when(orderRepository.findById(order_id)).thenReturn(Optional.empty());
        var res = service.UpdateStatus("preparing", order_id.toString());
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

    @Test
    public void InvalidIdTest() {
        var res = service.UpdateStatus("preparing", "test");
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }
}
