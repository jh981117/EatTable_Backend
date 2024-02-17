package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lec.spring.domain.listener.UserEntityListener;
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
@EntityListeners(value = UserEntityListener.class)
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

    // OAuth2
    private String oauth;
    private String oauthId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "userRole",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles = new HashSet<>();

    // 새로운 Role을 추가하는 메서드  권한 추가
    public void addRole(Role role) {
        this.roles.add(role);
    }


    @PrePersist
    void prePersist() {
        if (this.profileImageUrl == null) {
            this.profileImageUrl = "https://eatablebucket.s3.ap-northeast-2.amazonaws.com/userdefault.png";
        }
        if (this.temperature == 0) {
            this.temperature = 10;
        }
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    private  List<UserHistory> userHistories = new ArrayList<>();



}
