package dishTest;

import com.github.matheuswwwp.dinenow.conf.fileUpload.FileUpload;
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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CreateDishTest {
    @InjectMocks
    private DishService service;
    @Mock
    private DishRepository repository;
    @Mock
    private FileUpload fileUpload;

    @Test
    public void success() throws IOException {
        var uuid = UUID.randomUUID();
        Path imgDirectory = Paths.get("./src/test/java/dishTest/img");System.out.println("Diretório de trabalho atual: " + System.getProperty("user.dir"));
        Path imagePath = imgDirectory.resolve("dish.jpg");
        byte[] imageBytes = Files.readAllBytes(imagePath);
        MultipartFile file = new MockMultipartFile("file", imagePath.getFileName().toString(), "image/jpg", imageBytes);
        MultipartFile[] files = new MultipartFile[]{file};
        Float price = 10F;
        Dish dish = new Dish(uuid, price, "test", "test");
        Mockito.when(repository.save(dish)).thenReturn(dish);
        when(fileUpload.handle(Mockito.any(MultipartFile[].class), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(null));
        var res = service.CreateDish(dish, files);
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
    }
}
