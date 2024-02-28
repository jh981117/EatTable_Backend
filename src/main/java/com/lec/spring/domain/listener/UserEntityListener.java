package com.lec.spring.domain.listener;

import com.lec.spring.domain.User;
import com.lec.spring.domain.UserHistory;
import com.lec.spring.repository.UserHistoryRepository;
import com.lec.spring.util.BeanUtil;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;

public class UserEntityListener {

    public void updateAndPersist(Object o){
        UserHistoryRepository userHistoryRepository = BeanUtil.getBean(UserHistoryRepository.class);

        User user = (User) o;

        UserHistory userHistory = new UserHistory();
        userHistory.setName(user.getName());
        userHistory.setId(user.getId());
        userHistoryRepository.save(userHistory);
    }
}
