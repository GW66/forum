package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.PaginationDTO;
import com.gw.forum.forum.dto.QuestionDTO;
import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String index(@RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "6")Integer size,
                        HttpServletRequest request,
                        Model model){
//        获取Cookie
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
//        问题显示
        List<QuestionDTO> questionDTOList=questionService.listpage(page,size);
        model.addAttribute("questionDtoList",questionDTOList);
//        分页显示
        PaginationDTO paginationDTO=questionService.showpage(page,size);
        model.addAttribute("paginationDTO",paginationDTO);
        return "index";
    }
}
