package cartTest;

import com.github.matheuswwwp.dinenow.model.cart.Cart;
import com.github.matheuswwwp.dinenow.repository.cart.CartRepository;
import com.github.matheuswwwp.dinenow.service.cart.CartService;
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
public class DeleteCartTest {
    @InjectMocks
    private CartService service;
    @Mock
    private CartRepository cartRepository;

    @Test
    public void DeleteCartSuccess() {
        var user_id = UUID.randomUUID();
        var cart_id = UUID.randomUUID();
        var cart = new Cart();
        Mockito.when(cartRepository.findByIdAndUserId(cart_id, user_id)).thenReturn(Optional.of(cart));
        Mockito.doNothing().when(cartRepository).deleteById(cart_id);
        var res = service.DeleteCart(user_id.toString(), cart_id.toString());
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    public void CartNotFound() {
        var user_id = UUID.randomUUID();
        var cart_id = UUID.randomUUID();
        Mockito.when(cartRepository.findByIdAndUserId(cart_id, user_id)).thenReturn(Optional.empty());
        var res = service.DeleteCart(user_id.toString(), cart_id.toString());
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

    @Test
    public void InvalidIdTest() {
        var res = service.DeleteCart("test", "test");
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }
}
