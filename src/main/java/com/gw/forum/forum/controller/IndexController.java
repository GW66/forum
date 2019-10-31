package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.PaginationDTO;
import com.gw.forum.forum.dto.QuestionDTO;
import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.model.UserExample;
import com.gw.forum.forum.service.NotificationService;
import com.gw.forum.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/")
    public String index(@RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "6")Integer size,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Model model){
//        获取Cookie
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(cookie.getValue());
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size()!=0){
                        //            创建session
                        HttpSession session=request.getSession();
                        session.setAttribute("user",users.get(0));
                        //        统计未读数据
                        Long unreadCount=notificationService.unreadCount(users.get(0).getId());
                        session.setAttribute("unreadCount",unreadCount);

                    }else {
                        Cookie token=new Cookie("token",null);
                        token.setMaxAge(0);
                        response.addCookie(token);
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
