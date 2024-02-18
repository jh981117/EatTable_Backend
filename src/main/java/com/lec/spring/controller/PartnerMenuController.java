package com.lec.spring.controller;


import com.lec.spring.domain.PartnerMenu;
import com.lec.spring.service.PartnerMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/partner")
@CrossOrigin // cross-origin 요청 허용
public class PartnerMenuController {

    private final PartnerMenuService partnerMenuService;


    // 기존 메뉴 가져오기
    @GetMapping("/partnerMenuList/{partnerId}")
    public ResponseEntity<?> partnerMenuList(@PathVariable Long partnerId) {
        return new ResponseEntity<>(partnerMenuService.getMenuListByPartnerId(partnerId), HttpStatus.OK);
    }

    // 새로운 메뉴 추가 기능
    @PostMapping("/addMenu")
    public ResponseEntity<?> addMenu(@RequestBody Map<String, Object> menuData) {
        try {
            Long partnerId = Long.parseLong(menuData.get("partnerId").toString());
            String name = menuData.get("name").toString();
            String price = menuData.get("price").toString();
            String imageURL = menuData.get("menuImageUrl").toString(); // 이미지 URL을 받습니다.

            // 이미지 파일 데이터를 처리하는 방법은 클라이언트에서 선택한 방법에 따라 다릅니다.
            // 여기서는 이미지 URL을 직접 받아서 사용합니다.

            // PartnerMenuService의 saveMenu 메서드를 호출하여 데이터를 저장하고 응답을 반환합니다.
            PartnerMenu menu = partnerMenuService.saveMenu(partnerId, name, price, imageURL);
            return new ResponseEntity<>(menu, HttpStatus.OK);
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
    @PutMapping("/updateMenu/{id}")
    public ResponseEntity<?> updateMenu(
            @PathVariable Long id,
            @RequestBody Map<String, Object> menuData) { // JSON body를 받습니다.
        try {
            String name = menuData.get("name").toString();
            String price = menuData.get("price").toString();
            String imageURL = menuData.get("imageURL").toString(); // 이미지 URL을 받습니다.

            // PartnerMenuService의 updateMenu 메서드를 호출하여 데이터를 수정하고 응답을 반환합니다.
            PartnerMenu menu = partnerMenuService.updateMenu(id, name, price, imageURL);
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update menu: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
