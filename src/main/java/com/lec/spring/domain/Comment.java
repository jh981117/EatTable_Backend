package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;


    @ManyToOne()
    @JoinColumn(name = "partnerreviewid") // 외래 키를 명시합니다
    private PartnerReview partnerReview;

    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    @JsonIgnore
    private User user;



}
