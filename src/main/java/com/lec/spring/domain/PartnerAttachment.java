package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


@Data
@Entity
public class PartnerAttachment {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String description;


    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name ="partner_info_id")
    private PartnerInfo partnerInfo;


}
