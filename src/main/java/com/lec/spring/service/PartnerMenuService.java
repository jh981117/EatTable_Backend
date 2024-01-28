package com.lec.spring.service;

import com.lec.spring.domain.Partner;
import com.lec.spring.domain.PartnerMenu;
import com.lec.spring.domain.PartnerMenuAttachment;
import com.lec.spring.repository.PartnerMenuAttachmentRepository;
import com.lec.spring.repository.PartnerMenuRepository;
import com.lec.spring.repository.PartnerRepository;
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
    private final PartnerRepository partnerRepository;


    private final PartnerMenuAttachmentRepository partnerMenuAttachmentRepository;


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

        //이미지 추가
        addFiles(files,partnerMenu.getId());

        return cnt;
    }


    //특정업체에 이미지 추가
    private void addFiles(Map<String, MultipartFile> files, Long id) {
        if (files != null) {
            for (var e : files.entrySet()) {
                if (!e.getKey().startsWith("upfile")) continue;

                // 첨부 파일 정보 출력
                System.out.println("\n첨부파일 정보: " + e.getKey());   // name값
                U.printFileInfo(e.getValue());   // 파일 정보 출력
                System.out.println();

                PartnerMenuAttachment file = upload(e.getValue());

                if (file != null) {
                    PartnerMenu partnerMenu = partnerMenuRepository.findById(id).orElse(null);
                    file.setPartnerMenu(partnerMenu);  //FK 설정
                    partnerMenuAttachmentRepository.save(file);
                }
            }
        }
    }


    // 물리적으로 파일 저장.  중복된 이름 rename 처리
    private PartnerMenuAttachment upload(MultipartFile multipartFile) {
        PartnerMenuAttachment partnerMenuAttachment = null;

        // 담긴 파일이 없으면 pass
        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename == null || originalFilename.length() == 0) return null;

        // 원본파일명
        String sourceName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        // 저장될 파일명
        String fileName = sourceName;

        // 파일명 이 중복되는지 확인
        File file = new File(menuUploadDir, sourceName);
        if(file.exists()){  // 이미 존재하는 파일명,  중복되면 다름 이름으로 변경하여 저장
            // a.txt => a_2378142783946.txt  : time stamp 값을 활용할거다!
            int pos = fileName.lastIndexOf(".");
            if(pos > -1){   // 확장자가 있는 경우
                String name = fileName.substring(0, pos);  // 파일 '이름'
                String ext = fileName.substring(pos + 1);   // 파일 '확장자'

                // 중복방지를 위한 새로운 이름 (현재시간 ms) 를 파일명에 추가
                fileName = name + "_" + System.currentTimeMillis() + "." + ext;
            } else {  // 확장자가 없는 경우
                fileName += "_" + System.currentTimeMillis();
            }
        }
        // 저장할 파일명
        System.out.println("fileName: " + fileName);

        // java.nio
        Path copyOfLocation = Paths.get(new File(menuUploadDir, fileName).getAbsolutePath());
        System.out.println(copyOfLocation);

        try {
            // inputStream을 가져와서
            // copyOfLocation (저장위치)로 파일을 쓴다.
            // copy의 옵션은 기존에 존재하면 REPLACE(대체한다), 오버라이딩 한다

            Files.copy(
                    multipartFile.getInputStream(),
                    copyOfLocation,
                    StandardCopyOption.REPLACE_EXISTING    // 기존에 존재하면 덮어쓰기
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        partnerMenuAttachment = PartnerMenuAttachment.builder()
                .filename(fileName)   // 저장된 이름
                .sourcename(sourceName)  // 원본 이름
                .build();

        return partnerMenuAttachment;
    }




    //이미지 파일 여부
    private void setImage(List<PartnerMenuAttachment> fileList){

        String realPath = new File(menuUploadDir).getAbsolutePath();

        for(var attachment : fileList){
            BufferedImage imgData = null;
            File f = new File(realPath, attachment.getFilename());
            try {
                imgData = ImageIO.read(f);
            } catch (IOException e) {
                System.out.println("파일존재안함: " + f.getAbsolutePath() + "[" + e.getMessage() + "]");
                throw new RuntimeException(e);
            }

            if(imgData != null) attachment.setImage(true);  // 이미지 여부 체크!
        }

        }

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



            // 새로운 첨부파일 추가
            addFiles(files, partnerMenu.getId());

            // 삭제할 첨부파일(들) 삭제
            if(delfile != null){
                for(Long fileId : delfile){
                    PartnerMenuAttachment file = partnerMenuAttachmentRepository.findById(fileId).orElse(null);
                    if(file != null){
                        delFile(file);   // 물리적으로 파일 삭제
                        partnerMenuAttachmentRepository.deleteById(fileId);
                    }
                }
            }
            result = 1;
        }

        return result;
    }

    // 특정 첨부파일(id) 를 물리적으로 삭제
    private void delFile(PartnerMenuAttachment file) {
        String saveDirectory = new File(menuUploadDir).getAbsolutePath();
        File f = new File(saveDirectory, file.getFilename());  // 물리적으로 저장된 파일들이 삭제 대상
        System.out.println("삭제시도--> " + f.getAbsolutePath());

        if(f.exists()){
            if(f.delete()){
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
            }
        } else {
            System.out.println("파일이 존재하지 않습니다.");
        }
    }

    // 특정 메뉴(id) 삭제

    public int deleteById(Long id) {
        int result = 0;
        PartnerMenu menu = partnerMenuRepository.findById(id).orElse(null);
        if(menu != null){  // 존재한다면 삭제 진행.
            // 물리적으로 저장된 첨부파일(들) 삭제
            List<PartnerMenuAttachment> fileList = partnerMenuAttachmentRepository.findByPartnerMenuId(id);
            if(fileList != null && fileList.size() > 0){
                for(PartnerMenuAttachment file : fileList){
                    delFile(file);
                }
            }
            // 글 삭제
            partnerMenuRepository.delete(menu);
            result = 1;
        }
        return result;
    }


}
