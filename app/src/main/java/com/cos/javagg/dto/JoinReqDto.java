package com.cos.javagg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JoinReqDto {
    private String username;
    private String password;
    private  String email;
}
