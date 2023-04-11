package com.stopbanner.src.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stopbanner.config.BaseException;
import com.stopbanner.src.domain.User;
import com.stopbanner.src.model.User.PostLoginReq;
import com.stopbanner.src.model.User.PostLoginRes;
import com.stopbanner.src.repository.UserRepository;
import com.stopbanner.src.security.SecurityUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.stopbanner.utils.JwtService;
import org.springframework.remoting.support.RemoteInvocationBasedExporter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;

import static com.stopbanner.config.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final SecurityUserDetailsService securityUserDetailsService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;

    public String loginSub(String sub) throws BaseException {
        try {
            User user = userRepository.findBySub(sub);
            return jwtService.createJwt(user.getSub());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostLoginRes login(String accessToken) throws BaseException {
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
                user.setIs_active(true);
                userRepository.save(user);
            }
            if (user.getIs_active() == false) {
                throw new BaseException(DISABLED_USER);
            }
            PostLoginRes postLoginRes = new PostLoginRes();
            postLoginRes.setToken(jwtService.createJwt(user.getSub()));
            return postLoginRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /*
    public PostUserPasswordRes createUser(PostUserPasswordReq postUserPasswordReq) throws BaseException {
        try {
            User user = new User();
            user.setName(postUserPasswordReq.getName());
            user.setCreateDate(LocalDateTime.now());
            user.setRoll("ROLE_USER");
            userRepository.save(user);
            PostUserPasswordRes postUserPasswordRes = new PostUserPasswordRes();
            postUserPasswordRes.setToken(jwtService.createJwt(user.getId()));
            return postUserPasswordRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    */

    /*
    public String loginGoogle(String sub) throws BaseException {
        Social social = socialRepository.findBySubAndSocialNum(sub, 0L);
        if (social == null) {
            throw new BaseException(FAILED_TO_GOOGLE_LOGIN);
        }
        log.info(social.getUser().getId().toString());
        return jwtService.createJwt(social.getUser().getId());
    }
    */

    /*
    // 회원정보 수정(Patch)
    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        String pwd;
        try {
            // 암호화: postUserReq에서 제공받은 비밀번호를 보안을 위해 암호화시켜 DB에 저장합니다.
            // ex) password123 -> dfhsjfkjdsnj4@!$!@chdsnjfwkenjfnsjfnjsd.fdsfaifsadjfjaf
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(patchUserReq.getPassword()); // 암호화코드
            patchUserReq.setPassword(pwd);
        } catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        try {
            int result = userDao.modifyUserName(patchUserReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 회원탈퇴(Delete)
    public void deleteUser(int userIdx) throws BaseException {
        try {
            int result = userDao.deleteUser(userIdx); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_DELETEUSER);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }*/
}
