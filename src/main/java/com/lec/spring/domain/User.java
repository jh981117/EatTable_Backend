package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String nickName;
    @Column(nullable = false)
    private String birthdate;
    @Column(nullable = false)
    private String email;

    //계정활성화?
    private boolean activated;

    @ColumnDefault(value = "10")
    private long temperature;

    private String profileImageUrl;
    private String bio;

    // 좋아요 목록
    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private List<ReviewLike> reviewLikes;



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "userRole",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles ;



}
