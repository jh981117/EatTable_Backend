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
    @MapsId("fromId")
    @JoinColumn(name = "fromId")
    private User fromId;

    @ManyToOne
    @MapsId("toId")
    @JoinColumn(name = "toId")
    private Partner toId;

}
