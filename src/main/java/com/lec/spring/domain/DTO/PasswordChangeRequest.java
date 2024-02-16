package com.lec.spring.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequest {
    @NotBlank
    private String username; // 사용자 이름

    @NotBlank
    private String oldPassword; // 현재 비밀번호

    @NotBlank
    private String newPassword; // 새로운 비밀번호
}
