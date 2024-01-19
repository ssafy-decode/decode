package com.decode.web.domain.image.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile image) throws IOException;
}
