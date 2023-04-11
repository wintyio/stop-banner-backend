package com.stopbanner.src.service;

import com.stopbanner.config.BaseException;
import com.stopbanner.src.domain.Post;
import com.stopbanner.src.domain.User;
import com.stopbanner.src.model.Post.PostCreateReq;
import com.stopbanner.src.model.Post.PostCreateRes;
import com.stopbanner.src.repository.*;
import com.stopbanner.src.security.SecurityUserDetailsService;
import com.stopbanner.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.stopbanner.config.BaseResponseStatus.DATABASE_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final CityRepository cityRepository;
    private final LocalRepository localRepository;
    public PostCreateRes createPost(PostCreateReq postCreateReq, String sub) throws BaseException {
        try {
            Post post = new Post();
            post.setUser(userRepository.findBySub(sub));
            post.setMember(memberRepository.getReferenceById(postCreateReq.getMemberId()));
            post.setImg(postCreateReq.getImg());
            post.setLat(postCreateReq.getLat());
            post.setLng(postCreateReq.getLng());
            post.setCity(cityRepository.getReferenceById(postCreateReq.getCityId()));
            post.setLocal(localRepository.getReferenceById(postCreateReq.getLocalId()));
            post.setAddress(postCreateReq.getAddress());
            post.setCreateDate(LocalDateTime.now());
            postRepository.save(post);
            PostCreateRes postCreateRes = new PostCreateRes(1L);
            return postCreateRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
