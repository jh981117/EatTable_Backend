package com.lec.spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PartnerLike {

    @EmbeddedId
    private PartnerLikeId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private User userId;

    @ManyToOne
    @MapsId("partnerId")
    @JoinColumn(name = "partnerId")
    private Partner partnerId;

}
