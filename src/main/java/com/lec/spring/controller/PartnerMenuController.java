package com.lec.spring.controller;


import com.lec.spring.domain.PartnerMenu;
import com.lec.spring.service.PartnerMenuService;
import com.lec.spring.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/partner")
@CrossOrigin // cross-origin 요청 허용
public class PartnerMenuController {

    private final PartnerMenuService partnerMenuService;
    private final S3Service s3Service;


    // 기존 메뉴 가져오기
    @GetMapping("/partnerMenuList/{partnerId}")
    public ResponseEntity<?> partnerMenuList(@PathVariable Long partnerId) {
        return new ResponseEntity<>(partnerMenuService.getMenuListByPartnerId(partnerId), HttpStatus.OK);
    }

    // 새로운 메뉴 추가 기능
    @PostMapping(value = "/addMenu/{partnerId}", consumes = "multipart/form-data")
    public ResponseEntity<?> addMenu(
            @PathVariable Long partnerId,
            @RequestParam("name") String name,
            @RequestParam("price") String price,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            String imageURL = null;
            // 파일 처리 로직 (파일을 저장하고, 저장된 파일의 URL을 반환)
            if (file != null && !file.isEmpty()) {
                imageURL =  s3Service.uploadFile(file);
            }

            // PartnerMenuService의 saveMenu 메서드를 호출하여 데이터를 저장하고 응답을 반환합니다.
            PartnerMenu menu = partnerMenuService.saveMenu(partnerId, name, price, imageURL);

            // 저장된 메뉴 정보를 맵에 담아 반환합니다.
            Map<String, Object> response = new HashMap<>();
            response.put("id", menu.getId());
            response.put("name", menu.getName());
            response.put("price", menu.getPrice());
            response.put("imageURL", menu.getMenuImageUrl());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add menu: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 메뉴 삭제 기능
    @DeleteMapping("/deleteMenu/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long id) {
        try {
            partnerMenuService.deleteMenu(id);
            return new ResponseEntity<>("Menu deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Menu not found", HttpStatus.NOT_FOUND);
        }
    }

    // 메뉴 수정 기능
    @PutMapping("/updateMenu/{partnerId}/{id}")
    public ResponseEntity<?> updateMenu(
            @PathVariable Long id,
            @PathVariable Long partnerId,
            @RequestBody Map<String, Object> menuData) { // JSON body를 받습니다.
        try {
            // 각 필드가 null이 아닌지 확인합니다.
            if (!menuData.containsKey("name") || !menuData.containsKey("price") || !menuData.containsKey("menuImageUrl")) {
                return new ResponseEntity<>("Failed to update menu: Required fields are missing", HttpStatus.BAD_REQUEST);
            }

            String name = menuData.get("name").toString();
            String price = menuData.get("price").toString();
            String menuImageUrl = menuData.get("menuImageUrl").toString(); // 이미지 URL을 받습니다.

            // PartnerMenuService의 updateMenu 메서드를 호출하여 데이터를 수정하고 응답을 반환합니다.
            PartnerMenu menu = partnerMenuService.updateMenu(id, name, price, menuImageUrl);
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update menu: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
