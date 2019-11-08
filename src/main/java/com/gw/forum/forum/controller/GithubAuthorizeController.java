package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.GithubAccessTokenDTO;
import com.gw.forum.forum.dto.GithubUser;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.provider.GithubProvider;
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
public class GithubAuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private GithubAccessTokenDTO githubAccessTokenDTO;
    @Autowired
    private UserService userService;
    @GetMapping("/githubcallback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){
        githubAccessTokenDTO.setCode(code);
        githubAccessTokenDTO.setState(state);
        String accessToken=githubProvider.getAccessToken(githubAccessTokenDTO);
        GithubUser githubUser=githubProvider.getUser(accessToken);
        if(githubUser!=null){
            String token= UUID.randomUUID().toString();
//            创建获取cookie
            Cookie cookie=new Cookie("token",token);
            response.addCookie(cookie);
//            数据
            User user=new User();
            user.setAccountId(githubUser.getId());
            user.setName(githubUser.getLogin());
            user.setToken(token);
            user.setAvatarUrl(githubUser.getAvatar_url());
//            用户存放
            userService.userUpdate(user);
        }else {
            log.error("callback get github error,{}",githubUser);
        }
        return "redirect:/";
    }
}
