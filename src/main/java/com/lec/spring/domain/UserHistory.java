package com.lec.spring.domain;


import com.lec.spring.domain.listener.UserEntityListener;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.event.EventListener;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
public class UserHistory extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @ManyToOne
    private  User user;
}
