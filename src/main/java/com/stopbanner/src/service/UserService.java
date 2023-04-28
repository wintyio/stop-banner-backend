package com.stopbanner.src.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.stopbanner.config.BaseException;
import com.stopbanner.src.domain.User;
import com.stopbanner.src.model.Admin.PatchAdminActiveReq;
import com.stopbanner.src.model.Admin.PatchAdminActiveRes;
import com.stopbanner.src.model.User.*;
import com.stopbanner.src.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.stopbanner.utils.JwtService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

import static com.stopbanner.config.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public String loginSub(String sub) throws BaseException {
        try {
            User user = userRepository.findBySub(sub);
            return jwtService.createJwt(user.getSub());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostUserLoginRes login(String accessToken) throws BaseException {
        String sub = null;
        String requestURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";
            while ((line = buffer.readLine()) != null) {
                result +=line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            sub = element.getAsJsonObject().get("id").getAsString();

            // JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            // JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            // String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            // String email = kakao_account.getAsJsonObject().get("email").getAsString();
        } catch (Exception e) {
            throw new BaseException(FAILED_TO_KAKAO_LOGIN);
        }

        try {
            User user = userRepository.findBySub(sub);
            if (user == null) {
                user = new User();
                user.setSub(sub);
                user.setName("사용자");
                user.setRoll("ROLE_USER");
                user.setCreateDate(LocalDateTime.now());
                user.setActive(true);
                userRepository.save(user);
            }
            if (user.getActive() == false) {
                throw new BaseException(DISABLED_USER);
            }
            PostUserLoginRes postUserLoginRes = new PostUserLoginRes();
            postUserLoginRes.setToken(jwtService.createJwt(user.getSub()));
            return postUserLoginRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PatchUserNameRes updateName(PatchUserNameReq patchUserNameReq, String sub) throws BaseException {
        try {
            User user = userRepository.findBySub(sub);
            if (user == null) throw new BaseException(USER_NOT_FOUND);
            user.setName(patchUserNameReq.getName());
            userRepository.save(user);
            return new PatchUserNameRes(1L);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PatchAdminActiveRes updateActive(PatchAdminActiveReq patchAdminActiveReq) throws BaseException {
        try {
            User user = userRepository.findBySub(patchAdminActiveReq.getSub());
            if (user == null) throw new BaseException(USER_NOT_FOUND);
            user.setActive(patchAdminActiveReq.getActive());
            userRepository.save(user);
            return new PatchAdminActiveRes(1L);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}