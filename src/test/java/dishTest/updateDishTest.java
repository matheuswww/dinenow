package dishTest;

import com.github.matheuswwwp.dinenow.model.dish.Dish;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import com.github.matheuswwwp.dinenow.service.dish.DishService;
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
public class updateDishTest {
    @InjectMocks
    private DishService service;
    @Mock
    private DishRepository repository;

    @Test
    public void success() {
        var id = UUID.randomUUID();
        var dish = new Dish();
        dish.setDish_id(id);
        dish.setPrice(10F);
        dish.setActive(false);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(dish));
        Mockito.when(repository.updateDish(id, dish.getPrice(), dish.getActive())).thenReturn(1);
        var res = service.UpdateDish(dish);
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    public void noArgs() {
        var id = UUID.randomUUID();
        var dish = new Dish();
        dish.setDish_id(id);
        var res = service.UpdateDish(dish);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

    @Test
    public void notFound() {
        var id = UUID.randomUUID();
        var dish = new Dish();
        dish.setDish_id(id);
        dish.setPrice(10F);
        dish.setActive(false);
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        var res = service.UpdateDish(dish);
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }
}