package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Follow {
    @EmbeddedId
    private FollowId id;

    @ManyToOne
    @MapsId("fromId")
    @JoinColumn(name = "fromId")
    private User fromUser;

    @ManyToOne
    @MapsId("toId")
    @JoinColumn(name = "toId")
    private User toUser;


//



}
