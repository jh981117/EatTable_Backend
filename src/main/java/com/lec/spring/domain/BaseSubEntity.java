package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Data
@MappedSuperclass     //  상속받는 쪽에 임베디드 처럼 포함시켜준다.
@EntityListeners(AuditingEntityListener.class)
public class BaseSubEntity {
    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime updatedAt;
}
