package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserAttachment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
