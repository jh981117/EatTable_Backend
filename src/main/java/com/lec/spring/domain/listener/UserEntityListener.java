package com.lec.spring.domain.listener;

import com.lec.spring.domain.User;
import com.lec.spring.domain.UserHistory;
import com.lec.spring.repository.UserHistoryRepository;
import com.lec.spring.util.BeanUtil;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;

public class UserEntityListener {

    @PostUpdate
    @PostPersist
    public void postUpdateAndPersist(Object o){
        System.out.println("유저 엔티티 리스트너  포스업데이트 퍼시스트");

        UserHistoryRepository userHistoryRepository = BeanUtil.getBean(UserHistoryRepository.class);

        User user = (User)o;


        UserHistory userHistory = new UserHistory();
        userHistory.setName(user.getName());

    }


}
