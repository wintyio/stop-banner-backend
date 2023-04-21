package com.stopbanner.src.service;

import com.stopbanner.config.BaseException;
import com.stopbanner.src.domain.Member;
import com.stopbanner.src.domain.Post;
import com.stopbanner.src.model.Post.PostCreateReq;
import com.stopbanner.src.model.Post.PostCreateRes;
import com.stopbanner.src.model.Post.PostGetReq;
import com.stopbanner.src.model.Post.PostRes;
import com.stopbanner.src.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PartyRepository partyRepository;
    private final LocalRepository localRepository;
    public PostCreateRes createPost(PostCreateReq postCreateReq, String url, String sub) throws BaseException {
        try {
            Post post = new Post();
            post.setUser(userRepository.findBySub(sub));
            if (post.getUser() == null) {
                throw new BaseException(USER_NOT_FOUND);
            }
            post.setImg(url);
            post.setLat(postCreateReq.getLat());
            post.setLng(postCreateReq.getLng());
            post.setCity(cityRepository.getReferenceById(postCreateReq.getCityId()));
            post.setLocal(localRepository.getReferenceById(postCreateReq.getLocalId()));
            post.setAddress(postCreateReq.getAddress());
            post.setCreateDate(LocalDateTime.now());
            post.setLocal(localRepository.getReferenceById(postCreateReq.getLocalId()));
            post.setIsActive(true);
            postRepository.save(post);
            for (int i=0; i<postCreateReq.getNames().size(); i++) {
                memberService.createMember(postCreateReq.getNames().get(i), post.getId(), postCreateReq.getParties().get(i));
            }
            PostCreateRes postCreateRes = new PostCreateRes(1L);
            return postCreateRes;
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<PostRes> findAll(PostGetReq postGetReq) throws BaseException {
        try {
            List<Post> list = postRepository.findTop20ByIdLessThanAndIsActiveTrueOrderByIdDesc(postGetReq.getId());
            return list.stream().map(PostRes::new).collect(Collectors.toList());
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}