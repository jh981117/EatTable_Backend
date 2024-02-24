package com.lec.spring.domain.DTO;



import com.lec.spring.domain.Address;
import com.lec.spring.domain.Partner;
import com.lec.spring.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class PartnerWriteDto {
    private Long userId;
    private String storeName;
    private String partnerName;
    private String partnerPhone;
    private String storePhone;
    private String favorite;
    private Double lat;
    private Double lng;
    private String area;
    private String zipCode;
    private String district;



    public Partner toEntity(User user){
        Partner  partner = new Partner();
        partner.setUser(user);
        partner.setStoreName(storeName);
        partner.setPartnerName(partnerName);
        partner.setAddress(new Address(area,zipCode,lat,lng,district));
        partner.setPartnerPhone(partnerPhone);
        partner.setStorePhone(storePhone);
        partner.setFavorite(favorite);
        return partner;
    }

}