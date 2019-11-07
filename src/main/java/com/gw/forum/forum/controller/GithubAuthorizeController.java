package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.GithubAccessTokenDTO;
import com.gw.forum.forum.dto.GithubUser;
import com.gw.forum.forum.dto.GitlabAccessTokenDTO;
import com.gw.forum.forum.dto.GitlabUser;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.provider.GithubProvider;
import com.gw.forum.forum.provider.GitlabProvider;
import com.gw.forum.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
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
            user.setName(githubUser.getName());
            user.setToken(token);
            user.setAvatarUrl(githubUser.getAvatar_url());
//            用户存放
            userService.userUpdate(user);
        }
        return "redirect:/";
    }
}
