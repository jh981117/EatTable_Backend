package com.lec.spring.domain.DTO;



import com.lec.spring.domain.Address;
import com.lec.spring.domain.Partner;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class PartnerWriteDto {
    private String storeName;
    private String partnerName;
    private String partnerPhone;
    private String storePhone;
    private String favorite;
    private Double lat;
    private Double lng;
    private String area;
    private String zipCode;


    public Partner toEntity(){
        Partner  partner = new Partner();
        partner.setStoreName(storeName);
        partner.setPartnerName(partnerName);
        partner.setAddress(new Address(area,zipCode,lat,lng));
        partner.setPartnerPhone(partnerPhone);
        partner.setStorePhone(storePhone);
        partner.setFavorite(favorite);
        return partner;
    }

}