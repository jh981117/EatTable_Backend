package com.lec.spring.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
public class User extends BaseEntity {

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

    @Column(nullable = false)
    private String phone;

    //계정활성화?
    private boolean activated;

    @ColumnDefault(value = "10")
    private long temperature;

    private String profileImageUrl;
    private String bio;

    // 좋아요 목록
    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private List<ReviewLike> reviewLikes;

    // OAuth2
    private String oauth;
    private String oauthId;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "userRole",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles ;



    @PrePersist
    void prePersist() {
        if (this.profileImageUrl == null) {
            this.profileImageUrl = "https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png";
        }
    }



}
