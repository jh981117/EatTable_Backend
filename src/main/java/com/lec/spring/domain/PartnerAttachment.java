package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lec.spring.domain.Partner;
import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PartnerAttachment {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sourcename; // 원본 파일명
    private String filename;   // 저장된 파일명 (rename 된 파일명)
    private String imageUrl;
    private String description;

    private boolean isImage;   // 이미지

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name ="partnerId")
    private Partner partner;


}
