package com.lec.spring.service;

import com.lec.spring.domain.DTO.PartnerDto;
import com.lec.spring.domain.Partner;
import com.lec.spring.domain.PartnerAttachment;
import com.lec.spring.domain.PartnerReq;
import com.lec.spring.domain.TrueFalse;
import com.lec.spring.repository.PartnerAttachmentRepository;
import com.lec.spring.repository.PartnerRepository;
import com.lec.spring.repository.PartnerReqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PartnerService {


    private final PartnerAttachmentRepository partnerAttachmentRepository;
    private final PartnerRepository partnerRepository;
    private final S3Service s3Service;
    private final PartnerReqRepository partnerReqRepository;


    //매장리스트
    @Transactional
    public List<Partner> totallist() {

        return partnerRepository.findAll();

    }


    //매장리스트
    @Transactional
    public Page<Partner> list(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return partnerRepository.findByKeyword(keyword, pageable);
        } else {
            return partnerRepository.findAll(pageable);
        }
    }

    @Transactional
    public Page<Partner> homeList( Pageable pageable) {


            return partnerRepository.findAll(pageable);

    }

    //매장등록

    @Transactional
    public Partner write(Partner partner) {
        return partnerRepository.save(partner);
    }

    //매장디테일 정보
    @Transactional
    public PartnerDto detail(Long id){
        Partner partner = partnerRepository.findById(id).orElse(null);

        PartnerDto partnerDto = PartnerDto.builder()
                .id(partner.getId())
                .storeName(partner.getStoreName())
                .partnerName(partner.getPartnerName())
                .userId(partner.getUser().getId())
                .storePhone(partner.getStorePhone())
                .partnerPhone(partner.getPartnerPhone())
                .address(partner.getAddress())
                .tableCnt(partner.getTableCnt())
                .openTime(partner.getOpenTime())
                .storeInfo(partner.getStoreInfo())
                .reserveInfo(partner.getReserveInfo())
                .favorite(partner.getFavorite())
                .viewCnt(partner.getViewCnt())
                .parking(partner.getParking())
                .corkCharge(partner.getCorkCharge())
                .dog(partner.getDog())
                .partnerState(partner.getPartnerState())
                .fileList(partner.getFileList())
                .build();

        return partnerDto;
    }


    //매장정보 수정  ( 이미지추후 추가, 고민중)
    @Transactional
    public Partner update(Partner partner, List<MultipartFile> files) {

        System.out.println(partner + "파트너");
        System.out.println(files + "파일");
        // 파트너 ID로 해당 파트너를 찾아옴
        Partner partnerUpdate = partnerRepository.findById(partner.getId())
                .orElseThrow(() -> new IllegalArgumentException("Partner not found with id: " + partner.getId()));

        // 파일 저장 로직 추가
        for (MultipartFile file : files) {
            PartnerAttachment partnerAttachment = new PartnerAttachment();
            String s3StoragePath = s3Service.uploadFile(file);
            partnerAttachment.setImageUrl(s3StoragePath);
            partnerAttachment.setPartner(partner);
            partnerAttachment.setFilename(file.getOriginalFilename());
            partnerAttachment.setImage(true);
            partnerAttachmentRepository.save(partnerAttachment);
            }

        //변경감지  -- getter
        // 파트너 정보 업데이트
        partnerUpdate.setStoreName(partner.getStoreName());
        partnerUpdate.setPartnerName(partner.getPartnerName());
        partnerUpdate.setPartnerPhone(partner.getPartnerPhone());
        partnerUpdate.setStorePhone(partner.getStorePhone());
        partnerUpdate.setParking(partner.getParking());
        partnerUpdate.setReserveInfo(partner.getReserveInfo());
        partnerUpdate.setStoreInfo(partner.getStoreInfo());
        partnerUpdate.setDog(partner.getDog());
        partnerUpdate.setFavorite(partner.getFavorite());
        partnerUpdate.setCorkCharge(partner.getCorkCharge());
        partnerUpdate.setTableCnt(partner.getTableCnt());
        partnerUpdate.setOpenTime(partner.getOpenTime());

        // 업데이트된 파트너 정보를 반환
        return partnerUpdate;
    }

    //직접 삭제불사  삭제신청만가능 삭제신청은 partnerReq 에서
    @Transactional
    public String delete(Long id) {
        Partner partnerDelete = partnerRepository.findById(id).orElse(null);
        if (partnerDelete != null) {
            partnerRepository.deleteById(id);
            return "1";
        }
        return "0";
    }


    
    public String remove(Long imageId) {
        PartnerAttachment partnerRemove = partnerAttachmentRepository.findById(imageId).orElse(null);
        if (partnerRemove != null) {
            partnerAttachmentRepository.deleteById(imageId);
            System.out.println(imageId);
            return "1";
        }
        return "0";
    }

//    @Transactional
//    public void updateImage(Long imageId, MultipartFile file) {
//        // 이미지 ID로 해당 이미지를 조회합니다.
//        PartnerAttachment attachment = partnerAttachmentRepository.findById(imageId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 이미지를 찾을 수 없습니다: " + imageId));
//
//        // S3에 파일을 업로드하고 이미지 URL을 업데이트합니다.
//        String s3StoragePath = s3Service.uploadFile(file);
//        attachment.setImageUrl(s3StoragePath);
//        attachment.setFilename(file.getOriginalFilename());
//        partnerAttachmentRepository.save(attachment);
//    }
public List<PartnerDto> getPartnersByUserId(Long userId) {
    List<Partner> partners = partnerRepository.findByUserId(userId); // 사용자 ID로 파트너 목록 조회
    return partners.stream().map(partner -> PartnerDto.builder()
                    .id(partner.getId())
                    .storeName(partner.getStoreName())
                    .partnerName(partner.getPartnerName())
                    .userId(userId) // userId 설정
                    .partnerPhone(partner.getPartnerPhone())
                    .storePhone(partner.getStorePhone())
                    .address(partner.getAddress())
                    .tableCnt(partner.getTableCnt())
                    .openTime(partner.getOpenTime())
                    .storeInfo(partner.getStoreInfo())
                    .reserveInfo(partner.getReserveInfo())
                    .favorite(partner.getFavorite())
                    .viewCnt(partner.getViewCnt())
                    .parking(partner.getParking())
                    .createdAt(partner.getCreatedAt()) // BaseEntity 필드 설정
                    .updatedAt(partner.getUpdatedAt()) // BaseEntity 필드 설정
                    .corkCharge(partner.getCorkCharge())
                    .dog(partner.getDog())
                    .partnerState(partner.getPartnerState())
                    .fileList(new ArrayList<>(partner.getFileList())) // 이 부분은 실제 PartnerAttachment 객체 리스트를 PartnerDto의 리스트 형태로 변환하는 로직이 필요할 수 있습니다.
                    .build())
            .collect(Collectors.toList());
}


    public Partner stateUpdate(Long id) {
        List<Partner> partners = partnerRepository.findByUserId(id);
        Partner partner = partners.get(0); // 여기서는 첫 번째 파트너만 가져옵니다.
        partner.setPartnerState(TrueFalse.FALSE);
        return partnerRepository.save(partner); //
    }
}
