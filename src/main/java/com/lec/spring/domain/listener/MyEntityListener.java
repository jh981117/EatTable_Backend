package com.lec.spring.domain.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class MyEntityListener {

    @PrePersist
    public void prePersist(Object o){  // 반드시 Object 매개변수 필요
        System.out.println(">> MyEntityListener#prePersist");
        if(o instanceof Auditable){
            ((Auditable)o).setUpdatedAt(LocalDateTime.now());
            ((Auditable)o).setCreatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object o){
        System.out.println(">> MyEntityListener#preUpdate");
        if(o instanceof Auditable){
            ((Auditable)o).setUpdatedAt(LocalDateTime.now());
        }
    }
}
