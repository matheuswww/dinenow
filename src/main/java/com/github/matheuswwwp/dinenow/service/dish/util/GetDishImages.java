package com.github.matheuswwwp.dinenow.service.dish.util;

import com.github.matheuswwwp.dinenow.DTO.dish.GetAllDishDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetDishImages {
    @Value("${upload.path}")
    private String path;

    @Value("${url}")
    private String url;

    public void GetDishImages(List<GetAllDishDTO> dishes) {
        for (GetAllDishDTO dish: dishes) {
            File folder = new File(path+dish.getId());
            List<String> links = dish.getImages();
            if(folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if(files != null) {
                    for (File file : files) {
                        if(file.isFile()) {
                            var urlPath = url+"/image/"+dish.getId()+"/"+file.getName();
                            if(dish.getImages() == null) {
                                links = new ArrayList<>();
                            }
                            links.add(urlPath);
                        }
                    }
                    dish.setImages(links);
                }
            }
        }
    }
}