package com.lec.spring.service;

import com.lec.spring.domain.DTO.PartnerReqDto;
import com.lec.spring.domain.PartnerReq;
import com.lec.spring.domain.PartnerReqState;
import com.lec.spring.domain.User;
import com.lec.spring.repository.PartnerReqRepository;
import com.lec.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;



@RequiredArgsConstructor
@Service
public class PartnerReqService {

    private final PartnerReqRepository partnerReqRepository;
    private final UserRepository userRepository;


    //전체 리스트
    @Transactional
    public List<PartnerReq> list() {
       return partnerReqRepository.findAll();
    }


    //상태에 따른 리스트  접수대기, 오픈, 취소대기, 닫음
    @Transactional
    public List<PartnerReqDto> listByStatus(String status) {
        List<PartnerReq> reqs;
        if ("OPEN_READY".equals(status)) {
            reqs = partnerReqRepository.findByPartnerReqState(PartnerReqState.OPEN_READY);

        }else if ("OPEN".equals(status)) {
            reqs = partnerReqRepository.findByPartnerReqState(PartnerReqState.OPEN);

        }else if ("CLOSE_READY".equals(status)){
            reqs = partnerReqRepository.findByPartnerReqState(PartnerReqState.CLOSE_READY);

        } else if ("CLOSE".equals(status)) {
            reqs = partnerReqRepository.findByPartnerReqState(PartnerReqState.CLOSE);

        }  else if ("WAIT".equals(status)) {
            reqs = partnerReqRepository.findByPartnerReqState(PartnerReqState.WAIT);

        }  else {
            return null;
        }
        return reqs.stream().map(req -> PartnerReqDto.builder()
                        .id(req.getId())
                        .managerName(req.getManagerName())
                        .storeName(req.getStoreName())
                        .userId(req.getUser().getId())
                        .memo(req.getMemo())
                        .phone(req.getPhone())
                        .partnerReqState(req.getPartnerReqState())
                         .build()
        ).collect(Collectors.toList());
    }


    //저장
    @Transactional
    public PartnerReq write (PartnerReq partnerDelete , Long userId){

        User user = userRepository.findById(userId).orElse(null);
        // PartnerReq 객체에 User 엔티티를 설정합니다.
        partnerDelete.setUser(user);

        return partnerReqRepository.save(partnerDelete);
    }


    //디테일 필요하면
    @Transactional
    public PartnerReq detail(Long id){
        return partnerReqRepository.findById(id).orElse(null);
    }


    //수정  상태만변경가능 ( 여기에서 취소사유 같은걸 컬럼추가 수정받아도 괜찮을듯해요)
    @Transactional
    public PartnerReq update(PartnerReq partnerReq){
        PartnerReq update = partnerReqRepository.findById(partnerReq.getId()).orElse(null);


        update.setPartnerReqState(partnerReq.getPartnerReqState());

        return update;

    }


    //딜리트 필요하면
    //삭제되면 1  아니면 0
    @Transactional
    public String delete(Long id){
        PartnerReq delete = partnerReqRepository.findById(id).orElse(null);
        if(delete != null){
            return "1";
        }
        return "0";

    }





}
