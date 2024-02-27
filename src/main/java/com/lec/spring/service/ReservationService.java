package com.lec.spring.service;

import com.lec.spring.domain.*;
import com.lec.spring.domain.DTO.ReservationDto;
import com.lec.spring.repository.PartnerRepository;
import com.lec.spring.repository.ReservationRepository;
import com.lec.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;
    private final SimpMessagingTemplate messagingTemplate;


    @Transactional
    public List<Reservation> findAllReservations(){return reservationRepository.findAll();}

    @Transactional
    public Reservation saveReservation(ReservationDto reservationDto, Long partnerId){

        User user = userRepository.findById(reservationDto.getUserId()).orElse(null);
        Partner partner = partnerRepository.findById(reservationDto.getPartnerId()).orElse(null);

        Reservation reservation = Reservation.builder()
                .people(reservationDto.getPeople())
                .reservationState(reservationDto.getReservationState().equals("True") ? TrueFalse.TRUE : TrueFalse.FALSE)
                .reservationRegDate(reservationDto.getReservationRegDate())
                .user(user)
                .partner(partner)
                .build();
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation updateReservation(Long id, TrueFalse newReservationState){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        reservation.setReservationState(newReservationState);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public List<Reservation> findUserReservation(Long userId){return reservationRepository.findByUserId(userId);}

    @Transactional
    public List<Reservation> findReservationsByPartnerId(Long partnerId) {
        return reservationRepository.findByPartnerId(partnerId);
    }

    @Transactional
    public void deleteByReservationIdAndPartnerId(Long reservationId, Long partnerId) {
        reservationRepository.deleteByReservationIdAndPartnerId(reservationId, partnerId);
    }

    @Transactional
    public int countReservationsByPartnerId(Long partnerId) {
        return reservationRepository.countReservationsByPartnerId(partnerId);
    }

    @Transactional
    public Reservation updateReservationState(Long reservationId, Long partnerId, String newReservationState) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + reservationId));

        // String 값을 TrueFalse 열거형으로 변환
        TrueFalse trueFalseState;
        try {
            trueFalseState = TrueFalse.valueOf(newReservationState.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid waiting state: " + newReservationState);
        }

        reservation.setReservationState(trueFalseState);
        // 다른 필요한 작업 수행

        return reservationRepository.save(reservation);
    }



    @Transactional
    public Long findCountWaiting (Long reservationId , Long partnerId) {
        return reservationRepository.countByUserId(reservationId , partnerId);
    }

    public List<Reservation> getReservationsByPartnerId(Long partnerId) {
        return reservationRepository.findByPartnerId(partnerId);
    }

    // 예약을 확정하고, 클라이언트에게 실시간으로 전송
    public void confirmReservation(Long reservationId) {
        // 예약을 확정하는 로직 구현
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null) {
            reservation.setReservationState(TrueFalse.TRUE); // 예약을 확정으로 설정
            reservationRepository.save(reservation);

            // 확정된 예약 정보를 웹소켓을 통해 클라이언트에게 전송
            messagingTemplate.convertAndSend("/topic/reservationConfirmation", reservation);
        }
    }
}
