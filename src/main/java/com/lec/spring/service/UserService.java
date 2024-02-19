package com.lec.spring.service;

import com.lec.spring.domain.*;
import com.lec.spring.repository.RoleRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.util.SecurityUtil;
import com.lec.spring.util.U;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class UserService {
//
//    @Value("${app.upload_user.path}")
//    private String userUploadDir;


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //유저 리스트목록
    @Transactional(readOnly = true)
    public List<User> list(){
        return userRepository.findAll();
    }


    //회원가입
    @Transactional
    public User signup(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }


        // 유저 정보를 만들어서 save
        User newUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .name(user.getName())
                .birthdate(user.getBirthdate())
                .email(user.getEmail())
                .phone(user.getPhone())
                .bio(user.getBio())
                .nickName(user.getNickName())
                .activated(true)
                .build();

        Role role = roleRepository.findByRoleName(RoleName.ROLE_MEMBER);

        newUser.setRoles(Collections.singleton(role));

        return userRepository.save(newUser);
    }


    // 유저 정보페이지
    @Transactional
    public User detail(Long id){
        return userRepository.findById(id).orElse(null);
    }



    //회원정보수정
    @Transactional
    public User update(User user){
        User userUpdate = userRepository.findById(user.getId()).orElse(null);
        userUpdate.setBio(user.getBio());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setNickName(user.getNickName());

        return userUpdate;
    }


    //회원탈퇴 //삭제되면 1  아니면 0
    @Transactional
    public String delete(Long id) {
        User userDelete = userRepository.findById(id).orElse(null);
        if(userDelete != null){

            userRepository.deleteById(id);
            return "1";
        }
        return "0";
    }



    //특정 유저 권한 가져오기
    @Transactional
    public List<Role> selectRolesById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        return roleRepository.findByUsers(user);
    }



    //username 회원아이디로 User정보 읽어오기
    @Transactional
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }



    // 사용자 인증
    @Transactional
    public boolean authenticateUser(String username, String oldPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            // passwordEncoder를 사용하여 입력된 이전 비밀번호와 저장된 비밀번호를 비교
            return passwordEncoder.matches(oldPassword, user.getPassword());
        }
        return false;
    }

    // 비밀번호 업데이트
    @Transactional
    public void updatePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            // 새 비밀번호를 암호화하여 저장
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }




    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }



}
