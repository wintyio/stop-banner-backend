package com.stopbanner.src.service;

import com.stopbanner.config.BaseException;
import com.stopbanner.src.domain.Forum;
import com.stopbanner.src.model.Forum.PostForumReq;
import com.stopbanner.src.model.Forum.PostForumRes;
import com.stopbanner.src.model.Forum.GetForumReq;
import com.stopbanner.src.model.Forum.GetForumRes;
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
public class ForumService {
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final MemberService memberService;
    private final LocalRepository localRepository;
    public PostForumRes createPost(PostForumReq postForumReq, String url, String sub) throws BaseException {
        try {
            Forum forum = new Forum();
            forum.setUser(userRepository.findBySub(sub));
            if (forum.getUser() == null) {
                throw new BaseException(USER_NOT_FOUND);
            }
            forum.setImg(url);
            forum.setLat(postForumReq.getLat());
            forum.setLng(postForumReq.getLng());
            // post.setCity(cityRepository.getReferenceById(postCreateReq.getCityId()));
            forum.setLocal(localRepository.getReferenceById(postForumReq.getLocalId()));
            forum.setAddress(postForumReq.getAddress());
            forum.setCreateDate(LocalDateTime.now());
            forum.setLocal(localRepository.getReferenceById(postForumReq.getLocalId()));
            forum.setIsActive(true);
            forumRepository.save(forum);
            for (int i = 0; i< postForumReq.getNames().size(); i++) {
                memberService.createMember(postForumReq.getNames().get(i), forum.getId(), postForumReq.getParties().get(i));
            }
            PostForumRes postForumRes = new PostForumRes(1L);
            return postForumRes;
        } catch (BaseException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetForumRes> findAll(GetForumReq getForumReq) throws BaseException {
        try {
            List<Forum> list = forumRepository.findTop20ByIdLessThanAndIsActiveTrueOrderByIdDesc(getForumReq.getId());
            return list.stream().map(GetForumRes::new).collect(Collectors.toList());
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}