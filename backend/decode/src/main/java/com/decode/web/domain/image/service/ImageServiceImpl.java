package com.decode.web.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final AmazonS3 amazonS3;
    private final int MAX_IMAGE_SIZE = 1_000_000 * 20; // 20mb

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(MultipartFile image) throws IOException {
        if (image.getSize() > MAX_IMAGE_SIZE) {
            throw new BadRequestException("이미지 크기는 20mb를 넘을 수 없습니다.");
        }
        String randomFileName = UUID.randomUUID().toString();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());
        metadata.setContentType(image.getContentType());

        amazonS3.putObject(bucket, randomFileName, image.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, randomFileName).toString();
    }
}