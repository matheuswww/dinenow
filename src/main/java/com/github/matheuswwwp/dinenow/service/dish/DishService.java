package com.github.matheuswwwp.dinenow.service.dish;

import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.fileUpload.FileUpload;
import com.github.matheuswwwp.dinenow.model.dish.Dish;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private FileUpload fileUpload;
    private static final Logger logger = LoggerFactory.getLogger(DishService.class);

    public ResponseEntity<?> CreateDish(Dish dish, MultipartFile []file) {
        try {
            Dish dishRepo = dishRepository.save(dish);
            String filePath = dishRepo.getId()+"/";
            return fileUpload.handle(file, filePath, "prato cadastrado porém não foi possível salvar a imagem");
        } catch (Exception e) {
            logger.error("CreateDish - error trying CreatedDish: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
