package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StoreReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @ColumnDefault(value = "0")
    private int score;

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
    private List<ReviewLike> reviewLikes;


    @PrePersist
    public void prePersist (){
        this.regDate = LocalDateTime.now();
    }




}
