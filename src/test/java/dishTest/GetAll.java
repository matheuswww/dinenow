package dishTest;

import com.github.matheuswwwp.dinenow.model.dish.Dish;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import com.github.matheuswwwp.dinenow.service.dish.DishService;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GetAll {
    @InjectMocks
    private DishService service;
    @Mock
    private DishRepository repository;
    @Mock
    private GetDishImages getDishImages;

    @Test
    public void success() {
        var uuid = UUID.randomUUID();
        List<Dish> mockDishes = new ArrayList<>();
        mockDishes.add(new Dish(uuid, 10F, "test", "test"));
        mockDishes.add(new Dish(uuid, 10F, "test", "test"));
        Page<Dish> mockPage = new PageImpl<>(mockDishes);
        Mockito.when(repository.findAll(PageRequest.of(0, 10))).thenReturn(mockPage);
        var res = service.GetAllDish(0,10);
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }
    @Test
    public void notFound() {
        Page<Dish> emptyPage = new PageImpl<>(Collections.emptyList());
        Mockito.when(repository.findAll(PageRequest.of(0, 10))).thenReturn(emptyPage);
        var res = service.GetAllDish(0,10);
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

    @Test
    public void pageAndItemsExceeded() {
        var res = service.GetAllDish(20,20);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }

}
