package com.lec.spring.service;

import com.lec.spring.domain.PartnerReq;
import com.lec.spring.domain.PartnerReqState;
import com.lec.spring.repository.PartnerReqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PartnerReqService {

    private final PartnerReqRepository partnerReqRepository;


    //전체 리스트
    @Transactional
    public List<PartnerReq> list() {
       return partnerReqRepository.findAll();
    }


    //상태에 따른 리스트  접수대기, 오픈, 취소대기, 닫음
    @Transactional
    public List<PartnerReq> listByStatus(String status) {
        if ("OPEN_READY".equals(status)) {
            return partnerReqRepository.findByPartnerReqState(PartnerReqState.OPEN_READY);

        }else if ("OPEN".equals(status)) {
            return partnerReqRepository.findByPartnerReqState(PartnerReqState.OPEN);

        }else if ("CLOSE_READY".equals(status)){
            return partnerReqRepository.findByPartnerReqState(PartnerReqState.CLOSE_READY);

        } else if ("CLOSE".equals(status)) {
            return partnerReqRepository.findByPartnerReqState(PartnerReqState.CLOSE);

        } else {
            return null;
        }
    }


    //저장
    @Transactional
    public PartnerReq write (PartnerReq partnerDelete){
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
