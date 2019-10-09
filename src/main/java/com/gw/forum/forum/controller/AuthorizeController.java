package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.AccessTokenDTO;
import com.gw.forum.forum.dto.GitlabUser;
import com.gw.forum.forum.provider.GitlabProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AuthorizeController {
    @Autowired
    private GitlabProvider gitlabProvider;
    @Autowired
    private AccessTokenDTO accessTokenDTO;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request ){
        accessTokenDTO.setCode(code);
        String accessToken=gitlabProvider.getAccessToken(accessTokenDTO);
        GitlabUser user=gitlabProvider.getUser(accessToken);
        String name=user.getName();
        System.out.println(name);
        HttpSession session=request.getSession();
        session.setAttribute("name",name);
        return "redirect:/";
    }
}
