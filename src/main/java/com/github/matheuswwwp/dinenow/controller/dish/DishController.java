package com.github.matheuswwwp.dinenow.controller.dish;

import com.github.matheuswwwp.dinenow.DTO.dish.CreateDishDTO;
import com.github.matheuswwwp.dinenow.DTO.dish.UpdateDishDTO;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.CustomValidator;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.conf.jwt.JwtTokenProvider;
import com.github.matheuswwwp.dinenow.conf.mapper.Mapper;
import com.github.matheuswwwp.dinenow.model.dish.Dish;
import com.github.matheuswwwp.dinenow.service.dish.DishService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dish")
public class DishController {
    private static final Logger logger = LoggerFactory.getLogger(DishController.class);
    private JwtTokenProvider tokenProvider;

    @Autowired
    private DishService dishService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse handleValidationException(MethodArgumentNotValidException ex) {
        return new CustomValidator().getMessage(ex);
    }

    @PostMapping(value = "/createDish", consumes = "multipart/form-data")
    public ResponseEntity<?> CreateDish(@ModelAttribute @Valid CreateDishDTO data) {
        logger.info("CreateDish - init CreateDish");
        var dishModel = Mapper.parseObject(data, Dish.class);
        return dishService.CreateDish(dishModel, data.getFile());
    }

    @PatchMapping(value = "/updateDish")
    public ResponseEntity<?> UpdateDish(@RequestBody @Valid UpdateDishDTO data) {
        logger.info("UpdateDish - init UpdateDish");
        try {
            UUID.fromString(data.getId());
        } catch (IllegalArgumentException e) {
            logger.error("UpdateDish - error trying UpdateDish: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse("id inválido", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
        }
        var dishModel = Mapper.parseObject(data, Dish.class);
        return dishService.UpdateDish(dishModel);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> GetAllDish(@RequestParam int pages, @RequestParam int items) {
        logger.info("GetAllDish - init GetAllDish");
        return dishService.GetAllDish(pages, items);
    }

    @GetMapping(value = "/getById")
    public ResponseEntity<?> GetById(@RequestParam String id) {
        logger.info("GetById - init GetById");
        return dishService.GetById(id);
    }
}