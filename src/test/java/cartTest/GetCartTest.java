package cartTest;

import com.github.matheuswwwp.dinenow.model.cart.Cart;
import com.github.matheuswwwp.dinenow.model.dish.Dish;
import com.github.matheuswwwp.dinenow.repository.cart.CartRepository;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import com.github.matheuswwwp.dinenow.service.cart.CartService;
import com.github.matheuswwwp.dinenow.service.dish.util.GetDishImages;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GetCartTest {
    @InjectMocks
    private CartService service;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private DishRepository dishRepository;
    @Mock
    private GetDishImages getDishImages;

    @Test
    public void success() {
        var user_id = UUID.randomUUID();
        List<Cart> cartList = new ArrayList<>();
        var cart = new Cart();
        var dish = new Dish();
        dish.setDish_id(UUID.randomUUID());
        cart.setId(UUID.randomUUID());
        cart.setDish(dish);
        cartList.add(cart);
        Mockito.when(cartRepository.findAllByUserId(user_id, PageRequest.of(0,10))).thenReturn(new PageImpl<>(cartList));
        Mockito.when(dishRepository.findById(cart.getDish().getDish_id())).thenReturn(Optional.of(dish));
        var res = service.GetCart(user_id.toString(), 0,10);
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    public void pageAndItemsExceeded() {
        var res = service.GetCart(UUID.randomUUID().toString(), 20,20);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    public void notFound() {
        var user_id = UUID.randomUUID();
        Mockito.when(cartRepository.findAllByUserId(user_id, PageRequest.of(0,10))).thenReturn(Page.empty());
        var res = service.GetCart(user_id.toString(), 0,10);
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

    @Test
    public void invalidIdTest() {
        var res = service.GetCart("test", 0,10);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

}
