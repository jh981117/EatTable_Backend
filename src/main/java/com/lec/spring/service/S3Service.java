package com.lec.spring.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
public class S3Service {
    private final AmazonS3 s3Client;
    private final String bucketName = "eatablebucket";

    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) {
        String fileName = generateFileName(file); // generateFileName 메서드 호출

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());

            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
            return s3Client.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("S3 파일 업로드 중 오류 발생", e);
        }
    }

    // 파일 이름을 생성하는 메서드
    private String generateFileName(MultipartFile file) {
        long currentTime = System.currentTimeMillis();
        String originalFileName = file.getOriginalFilename();
        String replacedFileName = originalFileName.replace(" ", "_");
        String uniqueFileName = currentTime + "-" + replacedFileName;
        return uniqueFileName;
    }


    // 이미지 삭제
    public void deleteImage(Long attachmentId) {
        // AWS 인증 설정 등 필요한 정보를 이용하여 S3 클라이언트를 빌드
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

        // S3에서 이미지 삭제를 위한 요청 생성
        String key = "attachments/" + attachmentId.toString(); // S3에 저장된 이미지의 키 (파일 경로)
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, key);

        // 이미지 삭제 요청 전송
        s3Client.deleteObject(deleteObjectRequest);
    }
}
