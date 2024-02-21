package com.lec.spring.service;

import com.lec.spring.domain.DTO.StoreReviewAttchmentDto;
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





}
