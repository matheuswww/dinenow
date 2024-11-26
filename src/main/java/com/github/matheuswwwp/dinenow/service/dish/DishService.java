package com.github.matheuswwwp.dinenow.service.dish;

import com.github.matheuswwwp.dinenow.DTO.dish.GetAllDishDTO;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.exception.customException.dish.PaginationExceeded;
import com.github.matheuswwwp.dinenow.conf.fileUpload.FileUpload;
import com.github.matheuswwwp.dinenow.conf.mapper.Mapper;
import com.github.matheuswwwp.dinenow.model.dish.Dish;
import com.github.matheuswwwp.dinenow.repository.dish.DishRepository;
import com.github.matheuswwwp.dinenow.service.dish.util.GetDishImages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    @Autowired
    private GetDishImages getDishImages;
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

    public ResponseEntity<?> GetAllDish(int pages, int items) {
        try {
            if (pages > 2 || items > 10) {
                throw new PaginationExceeded();
            }
            var dishRepo = dishRepository.findAll(PageRequest.of(pages, items));
            if(dishRepo.isEmpty()) {
                return new ResponseEntity<>(new RestResponse("nenhum prato foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
            }
            var dishList = dishRepo.toList();
            var dishDTO = Mapper.parseListObjects(dishList, GetAllDishDTO.class);
            getDishImages.GetDishImages(dishDTO);
            logger.info("GetAllDish - dish get with success");
            return ResponseEntity.status(HttpStatus.OK).body(dishDTO);
        } catch (PaginationExceeded e) {
            logger.error("GetAllDish - error trying GetAllDish: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("limite de páginas ou items excedidos", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("GetAllDish - error trying GetAllDish: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
