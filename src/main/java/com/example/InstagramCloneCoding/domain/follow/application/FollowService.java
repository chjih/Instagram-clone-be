package com.example.InstagramCloneCoding.domain.follow.application;

import com.example.InstagramCloneCoding.domain.follow.dao.FollowRepository;
import com.example.InstagramCloneCoding.domain.follow.domain.Follow;
import com.example.InstagramCloneCoding.domain.follow.domain.FollowId;
import com.example.InstagramCloneCoding.global.error.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.InstagramCloneCoding.domain.follow.error.FollowError.ALREADY_FOLLOWING;
import static com.example.InstagramCloneCoding.domain.follow.error.FollowError.NOT_FOLLOWING;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public void follow(String memberID, String followingId) {
        FollowId followId = new FollowId(memberID, followingId);
        // follow 중복 검사
        followRepository.findById(followId)
                .ifPresent(x -> {
                    throw new RestApiException(ALREADY_FOLLOWING);
                });

        followRepository.save(new Follow(memberID, followingId));
    }

    public void unfollow(String memeberId, String followingId) {
        FollowId followId = new FollowId(memeberId, followingId);
        // follow 확인
        if (followRepository.findById(followId).isEmpty()) {
            throw new RestApiException(NOT_FOLLOWING);
        }

        followRepository.delete(new Follow(memeberId, followingId));
    }

    public List<String> getFollowers(String memberId) {
        List<Follow> followerList = followRepository.findByFollowingId(memberId);
        return followerList.stream()
                .map(Follow::getFollowerId)
                .collect(Collectors.toList());
    }

    public List<String> getFollowings(String memberId) {
        List<Follow> followingList = followRepository.findByFollowerId(memberId);
        return followingList.stream()
                .map(Follow::getFollowingId)
                .collect(Collectors.toList());
    }

    public List<String> getFollowBacks(String memberId) {
        List<String> followBackList = getFollowings(memberId);
        followBackList.retainAll(getFollowers(memberId));
        return followBackList;
    }
}
