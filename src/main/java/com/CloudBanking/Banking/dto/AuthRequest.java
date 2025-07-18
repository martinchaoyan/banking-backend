package com.CloudBanking.Banking.dto;

import lombok.Data;


@Data
public class AuthRequest {
    private String email;
    private String password;
}
