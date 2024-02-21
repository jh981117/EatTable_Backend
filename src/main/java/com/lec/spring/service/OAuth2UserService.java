package com.lec.spring.service;

import com.lec.spring.domain.DTO.*;
import com.lec.spring.domain.Role;
import com.lec.spring.domain.RoleName;
import com.lec.spring.domain.User;
import com.lec.spring.repository.RoleRepository;
import com.lec.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public  OAuth2UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Value("${app.oauth2.password}")
    private String oauth2Password;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        System.out.println(oAuth2User);
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }else {
            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(RoleName.ROLE_MEMBER);

        if (existData == null) {
            User user = new User();
            user.setUsername(username);
            user.setEmail(oAuth2Response.getEmail());
            user.setName(oAuth2Response.getName());
            user.setBirthdate(oAuth2Response.getBirthday());
            user.setNickName(oAuth2Response.getNickName());
            user.setPassword(oauth2Password);
            user.setPhone(oAuth2Response.getPhone());
            user.setRoles(Collections.singleton(role));
            user.setActivated(true);

            userRepository.save(user);

            UserDto userDto = new UserDto();
            userDto.setUsername(username);
            userDto.setName(oAuth2User.getName());
            userDto.setBirthday(oAuth2Response.getBirthday());
            userDto.setEmail(oAuth2Response.getEmail());
            userDto.setNickname(oAuth2Response.getNickName());
            userDto.setPassword(oauth2Password);
            userDto.setPhone(oAuth2Response.getPhone());
            userDto.setRole(String.valueOf(RoleName.ROLE_MEMBER));
            userDto.setActivated(true);


            return new CustomOAuth2User(userDto);
        } else {


            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            userRepository.save(existData);

            UserDto userDto = new UserDto();
            userDto.setUsername(existData.getUsername());
            userDto.setName(oAuth2Response.getName());

            userDto.setEmail(oAuth2Response.getEmail());
            userDto.setPassword(oauth2Password);
            userDto.setPhone(oAuth2Response.getPhone());
            return new CustomOAuth2User(userDto);
        }
    }
}
