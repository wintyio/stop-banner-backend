package com.stopbanner.src.controller;

import com.stopbanner.config.BaseException;
import com.stopbanner.config.BaseResponse;
import com.stopbanner.config.BaseResponseStatus;
import com.stopbanner.src.model.Forum.PostForumReq;
import com.stopbanner.src.model.Forum.PostForumRes;
import com.stopbanner.src.model.Forum.GetForumReq;
import com.stopbanner.src.model.Forum.GetForumRes;
import com.stopbanner.src.security.SecurityUser;
import com.stopbanner.src.service.ForumService;
import com.stopbanner.src.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/forum")
public class ForumController {
    private final ForumService forumService;
    private final S3Service s3Service;

    public ForumController(ForumService forumService, S3Service s3Service) {
        this.forumService = forumService;
        this.s3Service = s3Service;
    }

    @PostMapping("")
    public BaseResponse<PostForumRes> postForum(@AuthenticationPrincipal SecurityUser securityUser,
                                                @Valid PostForumReq postForumReq) {
        try {
            String url = s3Service.upload(postForumReq.getImg());
            return new BaseResponse<>(forumService.createPost(postForumReq, url, securityUser.getUser().getSub()));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        } catch (IOException exception) {
            return new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping ("")
    public BaseResponse<List<GetForumRes>> getForum(@Valid GetForumReq getForumReq) {
        try {
            return new BaseResponse<>(forumService.findAll(getForumReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}