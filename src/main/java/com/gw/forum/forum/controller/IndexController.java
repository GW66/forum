package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.PaginationDTO;
import com.gw.forum.forum.dto.QuestionDTO;
import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.model.UserExample;
import com.gw.forum.forum.service.NotificationService;
import com.gw.forum.forum.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
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
    private QuestionService questionService;

    @GetMapping("/")
    public String index(@RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "6")Integer size,
                        @RequestParam(name = "search",required = false) String search,
                        Model model){
//        问题显示
        List<QuestionDTO> questionDTOList=questionService.listpage(search,page,size);
        model.addAttribute("questionDtoList",questionDTOList);
//        分页显示
        PaginationDTO paginationDTO=questionService.showpage(search,page,size);
        model.addAttribute("paginationDTO",paginationDTO);
//        search传值
        if (StringUtils.isBlank(search)) {
            search=null;
        }
        model.addAttribute("search", search);
        return "index";
    }
}
