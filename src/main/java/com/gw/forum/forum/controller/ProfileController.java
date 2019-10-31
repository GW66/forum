package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.NotificationDTO;
import com.gw.forum.forum.dto.PaginationDTO;
import com.gw.forum.forum.dto.QuestionDTO;
import com.gw.forum.forum.mapper.NotificationMapper;
import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.model.UserExample;
import com.gw.forum.forum.service.NotificationService;
import com.gw.forum.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}/")
    public String profile(@RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "6")Integer size,
                          @PathVariable("action") String action,
                          HttpServletRequest request,
                          Model model){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        Long creator= user.getId();
        switch (action){
            case "question":
//        问题显示
                List<QuestionDTO> questionDTOList=questionService.listpage(creator,page,size);
                model.addAttribute("questionDtoList",questionDTOList);
//        分页显示
                PaginationDTO paginationDTOQuestion=questionService.showpage(creator,page,size);
                model.addAttribute("paginationDTO",paginationDTOQuestion);
                model.addAttribute("section","question");
                model.addAttribute("sectionName","我的提问");
                break;
            case "revert":
//        问题显示
                List<NotificationDTO> notificationDTOList=notificationService.listpage(creator,page,size);
                model.addAttribute("notificationDTOList",notificationDTOList);
//        分页显示
                PaginationDTO paginationDTORevert=notificationService.showpage(creator,page,size);
                model.addAttribute("paginationDTO",paginationDTORevert);
                model.addAttribute("section","revert");
                model.addAttribute("sectionName","最新回复");
                break;
        }
        return "profile";
    }
}
