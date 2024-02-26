package com.lec.spring.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.lec.spring.domain.DTO.StoreReviewAttchmentDto;
import com.lec.spring.domain.DTO.StoreReviewDto;
import com.lec.spring.domain.PartnerAttachment;
import com.lec.spring.domain.PartnerReviewAttachment;
import com.lec.spring.domain.StoreReview;
import com.lec.spring.repository.PartnerReviewAttachmentRepository;
import com.lec.spring.repository.StoreReviewRepository;
import com.lec.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AttachmentService {



    private final PartnerReviewAttachmentRepository partnerReviewAttachmentRepository;
    private final StoreReviewRepository storeReviewRepository;
    private final S3Service s3Service;



    @Transactional
    public void saveAttachments(Long reviewId, List<MultipartFile> files)  {
        StoreReview storeReview = storeReviewRepository.findById(reviewId).orElse(null);


        for (MultipartFile file : files) {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String path = s3Service.uploadFile(file);

            PartnerReviewAttachment attachment = PartnerReviewAttachment.builder()
                    .filename(filename)
                    .imageUrl(path)
                    .isImage(true) // 여기서는 모든 파일을 이미지로 가정
                    .storeReview(storeReview)
                    .build();

            partnerReviewAttachmentRepository.save(attachment);
        }
    }


    // 리뷰 이미지 수정
    @Transactional
    public void updateAttachments(Long reviewId, List<MultipartFile> files) {
        System.out.println("리뷰아이디" + reviewId);
        System.out.println("파일 : " + files);

        StoreReview storeReview = storeReviewRepository.findById(reviewId).orElse(null);

        // 새로운 이미지 업로드
        for (MultipartFile file : files) {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String path = s3Service.uploadFile(file);   // s3.

            PartnerReviewAttachment attachment = PartnerReviewAttachment.builder()
                    .filename(filename)
                    .imageUrl(path)
                    .isImage(true) // 여기서는 모든 파일을 이미지로 가정
                    .storeReview(storeReview)
                    .build();

            partnerReviewAttachmentRepository.save(attachment);

        }
    }


    // 리뷰 이미지 삭제
    public String delete(Long imageId) {
        PartnerReviewAttachment deleteAttachment = partnerReviewAttachmentRepository.findById(imageId).orElse(null);
        if (deleteAttachment != null) {
            partnerReviewAttachmentRepository.deleteById(imageId);
            // 실제 이미지를 삭제.....
            s3Service.deleteImage(imageId);
            System.out.println("이미지ID값은? : " + imageId);
            return "1"; // 삭제
        }
        return "0"; //
    }

//     리뷰 이미지 삭제
//    @Transactional
//    public void deleteByImageId(PartnerReviewAttachment imageId) {
//
//        // 이미지 식별자를 사용하여 데이터베이스에서 이미지 정보를 검색
//        imageId =  partnerReviewAttachmentRepository.findById(imageId.getId()).orElse(null);
//        if (imageId != null) {
//            // 데이터베이스에서 이미지 정보 삭제
//            partnerReviewAttachmentRepository.delete(imageId);
//            // 실제 이미지를 삭제 (예시: Amazon S3를 사용하는 경우)
//            s3Service.delete(imageId.getImageUrl());
//        } else {
//            // 이미지가 존재하지 않을 경우 예외 처리 또는 메시지 반환
//            throw new NotFoundException("Image not found with id: " + imageId);
//        }
//    }

}
