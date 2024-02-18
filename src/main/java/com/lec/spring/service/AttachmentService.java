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
    public StoreReviewAttchmentDto storeReviewWrite (List<MultipartFile> fileList,Long userId, Long partnerId, Long storeId){


        for(MultipartFile file : fileList){
            PartnerReviewAttachment partnerReviewAttachment = new PartnerReviewAttachment();
            String s3StoragePath = s3Service.uploadFile(file);
            partnerReviewAttachment.setImageUrl(s3StoragePath);
//            partnerReviewAttachment.setPartner();

        }
        return null;

    }
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



    public List<StoreReviewAttchmentDto> findByStoreImg (Long storeId){
        List<PartnerReviewAttachment> attachments = partnerReviewAttachmentRepository.findByStoreReviewId(storeId);
        List<StoreReviewAttchmentDto> attachmentDtos = new ArrayList<>();
        for (PartnerReviewAttachment attachment : attachments) {
            StoreReviewAttchmentDto attachmentDto = StoreReviewAttchmentDto.builder()
                    .storeId(storeId)
                    .filename(attachment.getFilename())
                    .imageUrl(attachment.getImageUrl())
                    .isImage(attachment.isImage())
                    .build();
            attachmentDtos.add(attachmentDto);
        }
        return attachmentDtos;
    }

}
