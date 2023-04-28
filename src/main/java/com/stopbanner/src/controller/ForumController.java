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
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한다.")
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
    @ApiOperation(value = "게시글 조회", notes = "x보다 id가 작은 게시글 중에서 id가 제일 큰 20개 게시글을 조회한다.")
    public BaseResponse<List<GetForumRes>> getForum(@Valid GetForumReq getForumReq) {
        try {
            return new BaseResponse<>(forumService.findAll(getForumReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}