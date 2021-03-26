package com.cos.javagg.model.user;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private RoleType role; // ADMIN, USER
    private Timestamp createData;

}
