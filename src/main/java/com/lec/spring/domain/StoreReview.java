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
public class StoreReview extends BaseSubEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;


    @ColumnDefault(value = "0")
    private Long avg;
    ;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name ="partnerId")
    private Partner partner;



    //이미지
    @OneToMany(mappedBy = "storeReview",cascade = CascadeType.ALL,  orphanRemoval = true)

    private List<PartnerReviewAttachment> partnerReviewAttachments;



}
