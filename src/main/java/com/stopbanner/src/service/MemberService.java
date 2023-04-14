package com.stopbanner.src.service;

import com.stopbanner.config.BaseException;
import com.stopbanner.src.domain.Member;
import com.stopbanner.src.domain.Post;
import com.stopbanner.src.model.Post.PostCreateReq;
import com.stopbanner.src.model.Post.PostCreateRes;
import com.stopbanner.src.model.Post.PostRes;
import com.stopbanner.src.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.MemberRemoval;
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
public class MemberService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final MemberRepository memberRepository;
    private final CityRepository cityRepository;
    private final LocalRepository localRepository;
    public void createMember(String name, Long post, Long party) throws BaseException {
        try {
            System.out.println(name + " " + post + " " + party);
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
    public List<PostRes> findAll() throws BaseException {
        try {
            List<Post> list = postRepository.findAllByOrderByCreateDate();
            return list.stream().map(PostRes::new).collect(Collectors.toList());
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
