package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.AccessTokenDTO;
import com.gw.forum.forum.dto.GitlabUser;
import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.provider.GitlabProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GitlabProvider gitlabProvider;
    @Autowired
    private AccessTokenDTO accessTokenDTO;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           HttpServletResponse response){
        accessTokenDTO.setCode(code);
        String accessToken=gitlabProvider.getAccessToken(accessTokenDTO);
        GitlabUser gitlabUser=gitlabProvider.getUser(accessToken);
        if(gitlabUser!=null){
            String token= UUID.randomUUID().toString();

//            创建获取cookie
            Cookie cookie=new Cookie("token",token);
            response.addCookie(cookie);
            User user=new User();
            user.setAccountid(gitlabUser.getId());
            user.setName(gitlabUser.getName());
            user.setToken(token);
            user.setGmtcreate(System.currentTimeMillis());
            user.setGmtmodified(user.getGmtcreate());
            userMapper.insert(user);
        }
        return "redirect:/";
    }
}
