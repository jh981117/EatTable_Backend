package com.lec.spring.controller;


import com.lec.spring.domain.DTO.PartnerWriteDto;
import com.lec.spring.domain.Partner;

import com.lec.spring.domain.Role;
import com.lec.spring.domain.RoleName;
import com.lec.spring.domain.User;
import com.lec.spring.repository.RoleRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.domain.PartnerAttachment;
import com.lec.spring.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/partner")
@CrossOrigin // cross-origin 요청 허용
public class PartnerController {


    private final PartnerService partnerService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    //    매장리스트
    @GetMapping("/totallist")
    public ResponseEntity<?> totallist() {
        return new ResponseEntity<>(partnerService.totallist(), HttpStatus.OK);
    }




    @GetMapping("/list")
    public ResponseEntity<Page<Partner>> list(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(partnerService.list(keyword,pageable), HttpStatus.OK);
    }



    //매장등록


    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody PartnerWriteDto partnerWriteDto){
        User user = userRepository.findById(partnerWriteDto.getUserId()).orElse(null);
        Partner partner = partnerWriteDto.toEntity(user);
        Role partnerrole = roleRepository.findByRoleName(RoleName.ROLE_PARTNER);
        user.addRole(partnerrole);
        userRepository.save(user);
        return new ResponseEntity<>(partnerService.write(partner),HttpStatus.CREATED);
    }


    //매장정보 디테일
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        return new ResponseEntity<>(partnerService.detail(id), HttpStatus.OK);
    }

    //매장수정
    @PutMapping("/update")
    public ResponseEntity<?> update(@ModelAttribute Partner partner,
                                    @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        if (files == null) {
            // files가 null인 경우, 파일이 전송되지 않은 것으로 간주하고 빈 리스트로 초기화합니다.
            files = new ArrayList<>();
        }
        return new ResponseEntity<>(partnerService.update(partner, files), HttpStatus.OK);
    }



    //매장삭제  직접 x  신청받고 삭제가능
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return new ResponseEntity<>(partnerService.delete(id), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{imageId}")
    public ResponseEntity<?> remove(@PathVariable Long imageId) {
        return new ResponseEntity<>(partnerService.remove(imageId), HttpStatus.OK);
    }

//    @PutMapping("/updateImageUrl/{imageId}")
//    public ResponseEntity<String> updateImageUrl(
//            @PathVariable Long imageId,
//            @RequestParam("file") MultipartFile file
//    ) {
//        try {
//            // 이미지 업데이트를 서비스 계층에 위임합니다.
//            partnerService.updateImage(imageId, file);
//            return ResponseEntity.ok("이미지 업데이트 성공");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업데이트 실패");
//        }
//    }

    @PostMapping("/by-user")
    public ResponseEntity<List<Partner>> getPartnersByUser(@RequestBody Map<String, Long> requestBody) {
        Long userId = requestBody.get("userId");
        return new ResponseEntity<>(partnerService.getPartnersByUserId(userId), HttpStatus.OK);
    }
}
