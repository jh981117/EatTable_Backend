package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
public class PartnerReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String state;
    private String phone;
    private LocalDateTime regDate;






    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    @JsonIgnore
    private User user;
}
