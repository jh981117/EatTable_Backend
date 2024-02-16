package com.lec.spring.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class PartnerLikeId implements Serializable {
    private Long fromId;
    private Long toId;
}
