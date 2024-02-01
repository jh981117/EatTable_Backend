//package com.lec.spring.domain;
//
//import com.lec.spring.domain.User;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//public class UserAttachment {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String sourcename; // 원본 파일명
//    private String filename;   // 저장된 파일명 (rename 된 파일명)
//    private String imageUrl;
//
//
//
//
//    @OneToOne
//    @JoinColumn(name = "userId")
//    private User user;
//
//
//}
