package com.github.matheuswwwp.dinenow.conf.fileUpload;

import com.github.matheuswwwp.dinenow.conf.CustomValidator.HttpMessages;
import com.github.matheuswwwp.dinenow.conf.CustomValidator.RestResponse;
import com.github.matheuswwwp.dinenow.service.adminAuth.AdminAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
public class FileUpload {
    @Value("${upload.path}")
    private String path;
    private static final Logger logger = LoggerFactory.getLogger(AdminAuthService.class);
    
    public ResponseEntity<?> handle(MultipartFile[] files, String filePath, String errMsg) {
        try {
            for (MultipartFile file: files) {
                if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/jpg") && !file.getContentType().equals("image/png")) {
                    return new ResponseEntity<>(new RestResponse("invalid file extension", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
                }
                if(file.getSize() > 1024*1024*5) {
                    return new ResponseEntity<>(new RestResponse("file is too large.Limit is 5MB", HttpStatus.BAD_REQUEST.value(), HttpMessages.bad_request, null), HttpStatus.BAD_REQUEST);
                }
                new File(path+filePath).mkdir();
                file.transferTo(new java.io.File((path + filePath + file.getOriginalFilename())));
            }
            logger.info("handle - file saved with success");
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            logger.error("handle - error trying handle file: {}", e.getMessage());
            return new ResponseEntity<>(new RestResponse(errMsg, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpMessages.server_error, null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}