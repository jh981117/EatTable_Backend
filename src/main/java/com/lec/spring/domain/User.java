package com.lec.spring.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

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

    @ColumnDefault(value = "10")
    private long temperature;

    private String profileImageUrl;
    private String bio;

    // 좋아요 목록
    @OneToMany(mappedBy = "user")
    private List<ReviewLike> reviewLikes;



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "userRole",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private List<Role> roles;



}
