package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PartnerMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String price;



    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "partner_info_id")
    private PartnerInfo partnerInfo;


    @OneToOne(mappedBy = "partnerMenu")
    private PartnerMenuAttachment partnerMenuAttachment;



}
