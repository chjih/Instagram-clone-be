package com.example.InstagramCloneCoding.global.auth.service;

import org.springframework.stereotype.Service;

@Service
public class HeaderService {

    public String getAccessToken(String bearerToken){
        return bearerToken.substring(7);
    }
}