package com.lec.spring.service;

import com.lec.spring.domain.*;
import com.lec.spring.repository.RoleRepository;
import com.lec.spring.repository.UserAttachmenmtRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.util.U;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

@RequiredArgsConstructor
@Service
public class UserService {

    @Value("${app.upload_user.path}")
    private String userUploadDir;


    private final UserRepository userRepository;






    //유저 리스트목록
    @Transactional(readOnly = true)
    public List<User> list(){
        return userRepository.findAll();
    }


    //회원가입
    @Transactional
    public User signup (User user){
        return userRepository.save(user);
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
}
