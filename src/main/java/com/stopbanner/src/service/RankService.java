package com.stopbanner.src.service;

import com.stopbanner.config.BaseException;
import com.stopbanner.src.domain.Post;
import com.stopbanner.src.model.Member.GetRankPartyRes;
import com.stopbanner.src.model.Post.GetRankUserRes;
import com.stopbanner.src.model.Post.PostCreateReq;
import com.stopbanner.src.model.Post.PostCreateRes;
import com.stopbanner.src.model.Post.PostRes;
import com.stopbanner.src.model.Rank.GetRankNameRes;
import com.stopbanner.src.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.stopbanner.config.BaseResponseStatus.DATABASE_ERROR;
import static com.stopbanner.config.BaseResponseStatus.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RankService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PartyRepository partyRepository;
    private final LocalRepository localRepository;
    public List<GetRankUserRes> findRankUser() throws BaseException {
        try {
            List<Object[]> objectsList = postRepository.findRankUser();
            return objectsList.stream().map(GetRankUserRes::new).collect(Collectors.toList());
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

    public List<GetRankNameRes> findRankName() throws BaseException {
        try {
            List<Object[]> objectsList = memberRepository.findRankName();
            return objectsList.stream().map(GetRankNameRes::new).collect(Collectors.toList());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
