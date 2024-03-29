package com.example.InstagramCloneCoding.domain.follow.api;

import com.example.InstagramCloneCoding.domain.follow.application.FollowFindService;
import com.example.InstagramCloneCoding.domain.follow.application.FollowService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.member.dto.MemberResponseDto;
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
    private final FollowFindService followFindService;

    @PostMapping("/follow")
    public ResponseEntity<String> follow(@Parameter(hidden = true) @LoggedInUser Member member,
                                         @RequestParam(name = "memberid") String followingId) {
        followService.follow(member, followingId);

        return ResponseEntity.status(HttpStatus.OK)
                .body("following success!");
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollow(@Parameter(hidden = true) @LoggedInUser Member member,
                                           @RequestParam(name = "memberid") String followingId) {
        followService.unfollow(member, followingId);

        return ResponseEntity.status(HttpStatus.OK)
                .body("unfollow success!");
    }

    @DeleteMapping("/delete/follower")
    public ResponseEntity<String> deleteFollower(@Parameter(hidden = true) @LoggedInUser Member member,
                                                 @RequestParam(name = "memberid") String followerId) {
        followService.deleteFollow(followerId, member);

        return ResponseEntity.status(HttpStatus.OK)
                .body("delete follower success!");
    }

    @GetMapping("/getfollowers/{member_id}")
    public ResponseEntity<List<MemberResponseDto>> followers(@PathVariable("member_id") String memberId) {
        List<MemberResponseDto> followersId = followFindService.getFollowers(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(followersId);
    }

    @GetMapping("/getfollowings/{member_id}")
    public ResponseEntity<List<MemberResponseDto>> followings(@PathVariable("member_id") String memberId) {
        List<MemberResponseDto> followingsId = followFindService.getFollowings(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(followingsId);
    }
}