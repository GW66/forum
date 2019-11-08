package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.GitlabAccessTokenDTO;
import com.gw.forum.forum.dto.GitlabUser;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.provider.GitlabProvider;
import com.gw.forum.forum.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@Slf4j
public class GitlabAuthorizeController {
    @Autowired
    private GitlabProvider gitlabProvider;
    @Autowired
    private GitlabAccessTokenDTO gitlabAccessTokenDTO;
    @Autowired
    private UserService userService;
    @GetMapping("/gitlabcallback")
    public String callback(@RequestParam(name = "code") String code,
                           HttpServletResponse response){
        gitlabAccessTokenDTO.setCode(code);
        String accessToken=gitlabProvider.getAccessToken(gitlabAccessTokenDTO);
        GitlabUser gitlabUser=gitlabProvider.getUser(accessToken);
        if(gitlabUser!=null){
            String token= UUID.randomUUID().toString();
//            创建获取cookie
            Cookie cookie=new Cookie("token",token);
            response.addCookie(cookie);
//            数据
            User user=new User();
            user.setAccountId(gitlabUser.getId());
            user.setName(gitlabUser.getName());
            user.setToken(token);
            user.setAvatarUrl(gitlabUser.getAvatar_url());
//            用户存放
            userService.userUpdate(user);
        }else {
            log.error("callback get gitlab error,{}",gitlabUser);
        }
        return "redirect:/";
    }
}
