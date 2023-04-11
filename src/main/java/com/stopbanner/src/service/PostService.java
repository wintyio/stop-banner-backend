package com.stopbanner.src.service;

import com.stopbanner.config.BaseException;
import com.stopbanner.src.domain.Post;
import com.stopbanner.src.model.Post.PostCreateReq;
import com.stopbanner.src.model.Post.PostCreateRes;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final LocalRepository localRepository;
    public PostCreateRes createPost(PostCreateReq postCreateReq, String url, String sub) throws BaseException {
        try {
            Post post = new Post();
            post.setUser(userRepository.findBySub(sub));
            post.setImg(url);
            post.setLat(postCreateReq.getLat());
            post.setLng(postCreateReq.getLng());
            post.setCity(cityRepository.getReferenceById(postCreateReq.getCityId()));
            post.setLocal(localRepository.getReferenceById(postCreateReq.getLocalId()));
            post.setAddress(postCreateReq.getAddress());
            post.setCreateDate(LocalDateTime.now());
            post.setLocal(localRepository.getReferenceById(postCreateReq.getLocalId()));
            post.setIs_active(true);
            postRepository.save(post);
            PostCreateRes postCreateRes = new PostCreateRes(1L);
            return postCreateRes;
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
