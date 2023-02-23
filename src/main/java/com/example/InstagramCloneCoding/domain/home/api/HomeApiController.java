package com.example.InstagramCloneCoding.domain.home.api;

import com.example.InstagramCloneCoding.domain.follow.application.FollowService;
import com.example.InstagramCloneCoding.domain.home.application.HomeService;
import com.example.InstagramCloneCoding.domain.member.domain.Member;
import com.example.InstagramCloneCoding.domain.post.dto.PostResponseDto;
import com.example.InstagramCloneCoding.global.common.annotation.LoggedInUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeApiController {

    private final HomeService homeService;
    private final FollowService followService;

    @GetMapping("/homePosts")
    public ResponseEntity<List<PostResponseDto>> getPosts(@Parameter(hidden = true) @LoggedInUser Member member) {
        List<Member> followers = followService.getFollowers(member);
        List<PostResponseDto> posts = homeService.getHomePosts(member, followers);

        return ResponseEntity.status(HttpStatus.OK)
                .body(posts);
    }
}
