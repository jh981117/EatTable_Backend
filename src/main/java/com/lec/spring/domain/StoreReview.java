package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
public class StoreReview extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;


    @ColumnDefault(value = "0")
    private int avg;
    ;

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name ="partnerId")
    private Partner partner;


    // 좋아요 목록
    @OneToMany(mappedBy = "storeReview")
    @ToString.Exclude
    @JsonIgnore
    private List<ReviewLike> reviewLikes;


    //이미지
    @OneToMany(mappedBy = "storeReview")
    @ToString.Exclude
    @JsonIgnore
    private List<PartnerReviewAttachment> partnerReviewAttachments;



}
