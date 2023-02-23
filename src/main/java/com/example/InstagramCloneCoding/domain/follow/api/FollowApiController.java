package com.example.InstagramCloneCoding.domain.follow.api;

import com.example.InstagramCloneCoding.domain.follow.application.FollowService;
import com.example.InstagramCloneCoding.domain.follow.dto.FollowDto;
import com.example.InstagramCloneCoding.domain.member.application.MemberService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.global.common.annotation.LoggedInUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/relationship")
public class FollowApiController {

    private final FollowService followService;
    private final MemberService memberService;

    @GetMapping("/searchmember")
    public ResponseEntity<String> searchMember(@RequestParam String userId) {
        memberService.findMember(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body("member exist");
    }

    @PostMapping("/follow")
    public ResponseEntity<String> follow(@Parameter(hidden = true) @LoggedInUser Member member, @RequestBody FollowDto followDto) {
        Member following = memberService.findMember(followDto.getFollowingOrFollowerId());
        followService.follow(member, following);

        return ResponseEntity.status(HttpStatus.OK)
                .body("following success!");
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollow(@Parameter(hidden = true) @LoggedInUser Member member, @RequestBody FollowDto followDto) {
        Member following = memberService.findMember(followDto.getFollowingOrFollowerId());
        followService.unfollow(member, following);

        return ResponseEntity.status(HttpStatus.OK)
                .body("unfollow success!");
    }

    @GetMapping("/getfollowers")
    public ResponseEntity<List<String>> followers(@Parameter(hidden = true) @LoggedInUser Member member) {
        List<Member> followers = followService.getFollowers(member);
        List<String> followerIds = followService.getIds(followers);

        return ResponseEntity.status(HttpStatus.OK)
                .body(followerIds);
    }

    @GetMapping("/getfollowings")
    public ResponseEntity<List<String>> followings(@Parameter(hidden = true) @LoggedInUser Member member) {
        List<Member> followings = followService.getFollowings(member);
        List<String> followingIds = followService.getIds(followings);

        return ResponseEntity.status(HttpStatus.OK)
                .body(followingIds);
    }

    @GetMapping("/getfollowbacks")
    public ResponseEntity<List<String>> followBack(@Parameter(hidden = true) @LoggedInUser Member member) {
        List<Member> friends = followService.getFollowBacks(member);
        List<String> friendIds = followService.getIds(friends);

        return ResponseEntity.status(HttpStatus.OK)
                .body(friendIds);
    }
}