package com.github.matheuswwwp.dinenow.service.dish;

import com.github.matheuswwwp.dinenow.DTO.dish.GetDishDTO;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.exception.customException.dish.NoArgs;
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

import java.util.UUID;

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
            String filePath = dishRepo.getDish_id()+"/";
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
            var dishDTO = Mapper.parseListObjects(dishList, GetDishDTO.class);
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

    public ResponseEntity<?> GetById(String id) {
        try {
            var dishRepo = dishRepository.findById(UUID.fromString(id));
            if(dishRepo.isEmpty()) {
                return new ResponseEntity<>(new RestResponse("nenhum prato foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
            }
            var dishDTO = Mapper.parseListObjects(dishRepo.stream().toList(), GetDishDTO.class);
            getDishImages.GetDishImages(dishDTO);
            logger.info("GetDishById - dish get with success");
            return ResponseEntity.status(HttpStatus.OK).body(dishDTO.get(0));
        } catch (IllegalArgumentException e) {
            logger.error("GetDishById - error trying GetDishById: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("id inválido", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("GetDishById - error trying GetDishById: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> UpdateDish(Dish dish) {
        try {
            if(dish.getActive() == null && dish.getPrice() == null) {
                throw new NoArgs();
            }
            var dishRepo = dishRepository.findById(dish.getDish_id());
            if(dishRepo.isEmpty()) {
                return new ResponseEntity<>(new RestResponse("nenhum prato foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
            }
            dishRepository.updateDish(dish.getDish_id(), dish.getPrice(), dish.getActive());
            logger.info("UpdateDish - dish updated with success");
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (NoArgs e) {
            logger.error("UpdateDish - error trying updateDish: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("nenhum argumento foi passado", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("UpdateDish - error trying UpdateDish: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> DeleteDish(String id) {
        try {
            var uuid = UUID.fromString(id);
            var dishRepo = dishRepository.findById(uuid);
            if(dishRepo.isEmpty()) {
                return new ResponseEntity<>(new RestResponse("nenhum prato foi encontrado", HttpStatus.NOT_FOUND.value(), HttpMessages.not_found, null), HttpStatus.NOT_FOUND);
            }
            dishRepository.deleteById(uuid);
            logger.info("DeleteDish - dish deleted with success");
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (IllegalArgumentException e) {
            logger.error("DeleteDish - error trying DeleteDish: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("id inválido", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("DeleteDish - error trying DeleteDish: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("server error", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
