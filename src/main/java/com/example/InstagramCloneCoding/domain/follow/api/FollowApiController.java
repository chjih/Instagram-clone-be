package com.example.InstagramCloneCoding.domain.follow.api;

import com.example.InstagramCloneCoding.domain.follow.application.FollowService;
import com.example.InstagramCloneCoding.domain.follow.dto.FollowDto;
import com.example.InstagramCloneCoding.domain.member.application.MemberService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.global.common.annotation.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowApiController {

    private final FollowService followService;
    private final MemberService memberService;

    @PostMapping("/searchmember")
    public ResponseEntity<String> searchMember(@RequestParam String userId) {
        memberService.existMember(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body("member exist");
    }

    @PostMapping("/following")
    public ResponseEntity<String> following(@LoggedInUser Member member, @RequestBody FollowDto followDto) {
        memberService.existMember(followDto.getFollowingOrFollowerId());
        followService.follow(member.getUserId(), followDto.getFollowingOrFollowerId());

        return ResponseEntity.status(HttpStatus.OK)
                .body("following success!");
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollow(@LoggedInUser Member member, @RequestBody FollowDto followDto) {
        followService.unfollow(member.getUserId(), followDto.getFollowingOrFollowerId());

        return ResponseEntity.status(HttpStatus.OK)
                .body("unfollow success!");
    }

    @GetMapping("/followers")
    public ResponseEntity<List<String>> followers(@LoggedInUser Member member) {
        List<String> followers = followService.getFollowers(member.getUserId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(followers);
    }

    @GetMapping("/followings")
    public ResponseEntity<List<String>> followings(@LoggedInUser Member member) {
        List<String> followings = followService.getFollowings(member.getUserId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(followings);
    }

    @GetMapping("/followback")
    public ResponseEntity<List<String>> followBack(@LoggedInUser Member member) {
        List<String> friends = followService.getFollowBacks(member.getUserId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(friends);
    }
}