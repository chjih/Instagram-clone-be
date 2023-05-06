package com.example.InstagramCloneCoding.domain.member.dao;

import com.example.InstagramCloneCoding.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByUserIdOrEmail(String id, String email);

    List<Member> findByUserIdContainsOrNameContains(String id, String name);
}