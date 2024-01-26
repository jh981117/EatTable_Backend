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
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "followee_id")
    private User followee;


}
