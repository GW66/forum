package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.QuestionDTO;
import com.gw.forum.forum.mapper.QuestionMapper;
import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.Question;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model){
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
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
        List<QuestionDTO> questionDTOList=questionService.list();
        model.addAttribute("questionDtoList",questionDTOList);
        return "index";
    }
}
