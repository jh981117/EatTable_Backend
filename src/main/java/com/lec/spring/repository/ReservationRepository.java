package com.lec.spring.repository;

import com.lec.spring.domain.Reservation;
import com.lec.spring.domain.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    List<Reservation> findByPartnerId(Long partnerId);

    @Modifying
    @Query("DELETE FROM Reservation w WHERE w.id = :reservationId AND w.partner.id = :partnerId")
    void deleteByReservationIdAndPartnerId(@Param("reservationId") Long waitingId, @Param("partnerId") Long partnerId);

    int countReservationsByPartnerId(Long partnerId);


    @Query("SELECT COUNT(r)\n" +
            "FROM Reservation r\n" +
            "WHERE r.id < :reservationId\n" +
            "AND r.reservationState = 'WAITING' AND r.partner.id = :partnerId")
    Long countByUserId(@Param("reservationId") Long reservationId, @Param("partnerId") Long partnerId);


}
