package com.stopbanner.src.service;

import com.stopbanner.config.BaseException;
import com.stopbanner.src.domain.Member;
import com.stopbanner.src.domain.Report;
import com.stopbanner.src.model.Member.GetRankPartyRes;
import com.stopbanner.src.model.Report.ReportRes;
import com.stopbanner.src.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.stopbanner.config.BaseResponseStatus.DATABASE_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final MemberRepository memberRepository;
    private final CityRepository cityRepository;
    private final LocalRepository localRepository;
    public void createMember(String name, Long post, Long party) throws BaseException {
        try {
            Member member = new Member();
            member.setName(name);
            member.setPost(postRepository.getReferenceById(post));
            member.setParty(partyRepository.getReferenceById(party));
            member.setCreateDate(LocalDateTime.now());
            memberRepository.save(member);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetRankPartyRes> findRankParty() throws BaseException {
        try {
            List<Object[]> objectsList = memberRepository.findRankParty();
            return objectsList.stream().map(GetRankPartyRes::new).collect(Collectors.toList());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
