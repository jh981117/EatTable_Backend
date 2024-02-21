package com.lec.spring.service;

import com.lec.spring.domain.Partner;
import com.lec.spring.domain.PartnerLike;
import com.lec.spring.domain.PartnerLikeId;
import com.lec.spring.domain.User;
import com.lec.spring.repository.PartnerLikeRepository;
import com.lec.spring.repository.PartnerRepository;
import com.lec.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PartnerLikeService {

    private final PartnerLikeRepository partnerLikeRepository;
    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;


    @Transactional
    public boolean toggleStoreLike(Long userId, Long partnerId) {
        PartnerLikeId id = new PartnerLikeId(userId, partnerId);

        if (partnerLikeRepository.existsById(id)) {
            partnerLikeRepository.deleteById(id);
            return false; // 즐겨찾기 해제 상태 반환
        } else {
            User user = userRepository.findById(userId)
                    .orElse(null);
            Partner partner = partnerRepository.findById(partnerId)
                    .orElse(null);

            PartnerLike partnerLike = new PartnerLike(new PartnerLikeId(user.getId(), partner.getId()), user, partner);
            partnerLikeRepository.save(partnerLike);
            return true; // 즐겨찾기 설정 상태 반환
        }
    }

    public boolean checkStoreLikeState(Long userId, Long partnerId) {
        PartnerLikeId id = new PartnerLikeId(userId, partnerId);
        return partnerLikeRepository.existsById(id);
    }


    public ResponseEntity<?> findLength(Long partnerId) {
        long count = partnerLikeRepository.countByPartnerId(partnerId);
        return ResponseEntity.ok(count);
    }
}

