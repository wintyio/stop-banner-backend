package com.stopbanner.src.service;

import com.stopbanner.config.BaseException;
import com.stopbanner.src.domain.Member;
import com.stopbanner.src.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.stopbanner.config.BaseResponseStatus.DATABASE_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final ForumRepository forumRepository;
    private final PartyRepository partyRepository;
    private final MemberRepository memberRepository;
    public void createMember(String name, Long post, Long party) throws BaseException {
        try {
            Member member = new Member();
            member.setName(name);
            member.setForum(forumRepository.getReferenceById(post));
            member.setParty(partyRepository.getReferenceById(party));
            member.setCreateDate(LocalDateTime.now());
            memberRepository.save(member);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}