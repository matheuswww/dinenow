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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GetById {
    @InjectMocks
    private DishService service;
    @Mock
    private DishRepository repository;
    @Mock
    private GetDishImages getDishImages;

    @Test
    public void success() {
        var uuid = UUID.randomUUID();
        var mockDish = new Dish(uuid, 10F, "test", "test");
        Mockito.when(repository.findById(uuid)).thenReturn(Optional.of(mockDish));
        var res = service.GetById(uuid.toString());
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    public void notFound() {
        var uuid = UUID.randomUUID();
        Mockito.when(repository.findById(uuid)).thenReturn(Optional.empty());
        var res = service.GetById(uuid.toString());
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

    @Test
    public void invalidId() {
        var res = service.GetById("test");
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
    }
}
