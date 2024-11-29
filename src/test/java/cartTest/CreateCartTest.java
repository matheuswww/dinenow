package cartTest;

import com.github.matheuswwwp.dinenow.model.cart.Cart;
import com.github.matheuswwwp.dinenow.model.dish.Dish;
import com.github.matheuswwwp.dinenow.model.user.User;
import com.github.matheuswwwp.dinenow.repository.cart.CartRepository;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import com.github.matheuswwwp.dinenow.repository.user.UserRepository;
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
public class CreateCartTest {
    @InjectMocks
    private CartService service;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private DishRepository dishRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    public void CreateCartTest() {
        var user_id = UUID.randomUUID();
        var dish_id = UUID.randomUUID();
        var userRepo = new User(user_id, "test", "test@test.com", "test");
        var dishRepo = new Dish(dish_id, 10F, "test", "test");
        var cartRepo = new Cart(userRepo, dishRepo, 30.0F, 3);

        Mockito.when(dishRepository.findById(dish_id)).thenReturn(Optional.of(dishRepo));
        Mockito.when(userRepository.findById(user_id)).thenReturn(Optional.of(userRepo));
        Mockito.when(cartRepository.save(cartRepo)).thenReturn(cartRepo);

        var res = service.CreateCart(3, user_id.toString(), dish_id.toString());

        assertEquals(HttpStatus.CREATED, res.getStatusCode());
    }

    @Test
    public void DishNotFound() {
        var user_id = UUID.randomUUID();
        var dish_id = UUID.randomUUID();
        Mockito.when(dishRepository.findById(dish_id)).thenReturn(Optional.empty());
        var res = service.CreateCart(3, user_id.toString(), dish_id.toString());
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

    @Test
    public void InvalidIdTest() {
        var res = service.CreateCart(3,"test", "test");
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }
}
