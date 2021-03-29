package com.cos.javagg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JoinDto {
    private String username;
    private String password;
    private  String email;
}
