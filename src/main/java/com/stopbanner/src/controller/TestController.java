package com.stopbanner.src.controller;

import lombok.RequiredArgsConstructor;
import com.stopbanner.src.security.SecurityUser;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/test")
@RestController
public class TestController {

    @Secured("ROLE_USER")
    @GetMapping("/auth")
    public Object getTest2(@AuthenticationPrincipal SecurityUser securityUser) throws Exception {
        return securityUser;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/auth2")
    public Object getTest3(@AuthenticationPrincipal SecurityUser securityUser) throws Exception {
        return securityUser;
    }

    @GetMapping("")
    public Object getTest() throws Exception {
        return "yes";
    }
}