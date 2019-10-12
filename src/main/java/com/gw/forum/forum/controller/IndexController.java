package com.gw.forum.forum.controller;

import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        if(cookies!=cookies){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    User user=userMapper.findbytoken(cookie.getValue());
                    if (user!=null){
                        //            创建session
                        HttpSession session=request.getSession();
                        session.setAttribute("User",user);
                    }
                    break;
                }
            }
        }
        return "index";
    }
}
