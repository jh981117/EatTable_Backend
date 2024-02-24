package com.lec.spring.repository;

import com.lec.spring.domain.TrueFalse;
import com.lec.spring.domain.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface WaitingRepository extends JpaRepository<Waiting , Long> {
    List<Waiting> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    List<Waiting> findByPartnerId(Long partnerId);

    @Modifying
    @Query("DELETE FROM Waiting w WHERE w.id = :waitingId AND w.partner.id = :partnerId")
    void deleteByWaitingIdAndPartnerId(@Param("waitingId") Long waitingId, @Param("partnerId") Long partnerId);

    int countWaitingsByPartnerId(Long partnerId);


}
