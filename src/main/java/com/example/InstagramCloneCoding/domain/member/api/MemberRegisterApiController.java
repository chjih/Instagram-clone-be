package com.example.InstagramCloneCoding.domain.member.api;

import com.example.InstagramCloneCoding.domain.member.application.MemberRegisterService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("register")
public class MemberRegisterApiController {
    @Autowired
    private MemberRegisterService memberRegisterService;

    @PostMapping("")
    public ResponseEntity<Member> register(@RequestBody MemberRegisterDto registerDto) {
        System.out.println("dabin" + registerDto);
        return new ResponseEntity(memberRegisterService.register(registerDto), HttpStatus.OK);
    }
}
