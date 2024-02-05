package com.lec.spring.service;

import com.lec.spring.domain.Partner;
import com.lec.spring.domain.PartnerMenu;
import com.lec.spring.repository.PartnerMenuRepository;
import com.lec.spring.util.U;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PartnerMenuService {

    @Value("${app.upload_menu.path}")
    private String menuUploadDir;

    private final PartnerMenuRepository partnerMenuRepository;






    // 메뉴 전체리스트 필요할까?
    public List<PartnerMenu> list(){
        return partnerMenuRepository.findAll(Sort.by(Sort.Order.desc("id")));
    }


    //해당 업체 메뉴리스트 가져오기
    public List<PartnerMenu> getMenuListByPartner(Partner partner){
        return partnerMenuRepository.findByPartner(partner);
    }


    // 메뉴 저장
    public int write(PartnerMenu partnerMenu, Map<String, MultipartFile> files){

        PartnerMenu partnerMenu1 = partnerMenuRepository.save(partnerMenu);
        int cnt = (partnerMenu1 != null) ? 1 : 0;



        return cnt;
    }





    // 물리적으로 파일 저장.  중복된 이름 rename 처리




    //이미지 파일 여부


    // 메뉴 수정
    public int update(PartnerMenu partnerMenu  // <- id, subject, content
            , Map<String, MultipartFile> files  // 새로 추가된 첨부파일들
            , Long[] delfile) {  // 삭제될 첨부파일들의 id들
        int result = 0;

        PartnerMenu menu = partnerMenuRepository.findById(partnerMenu.getId()).orElse(null);

        if(menu != null){
            menu.setName(partnerMenu.getName());
            menu.setPrice(partnerMenu.getPrice());


            // userid와 레지데이터 업데이트





            // 삭제할 첨부파일(들) 삭제
            if(delfile != null){
                for(Long fileId : delfile){


                }
            }
            result = 1;
        }

        return result;
    }



    // 특정 메뉴(id) 삭제

    public int deleteById(Long id) {
        int result = 0;
        PartnerMenu menu = partnerMenuRepository.findById(id).orElse(null);
        if(menu != null){  // 존재한다면 삭제 진행.
            // 물리적으로 저장된 첨부파일(들) 삭제


            // 글 삭제
            partnerMenuRepository.delete(menu);
            result = 1;
        }
        return result;
    }


}
