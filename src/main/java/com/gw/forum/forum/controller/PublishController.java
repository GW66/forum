package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.QuestionDTO;
import com.gw.forum.forum.mapper.QuestionMapper;
import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.Question;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.model.UserExample;
import com.gw.forum.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public  String doPublish(@RequestParam(name = "title",required = false)String title,
                             @RequestParam(name = "description",required = false)String description,
                             @RequestParam(name = "tag",required = false)String tag,
                             @RequestParam(name = "id",required = false)Integer id,
                             HttpServletRequest request,
                             Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null || title=="" || description==null || description=="" || tag==null || tag==""){
            model.addAttribute("error","请完整填写信息");
            return "publish";
        }
        Question question=new Question();
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("token")){
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(cookie.getValue());
                    List<User> users = userMapper.selectByExample(userExample);
                    if(users.size()!=0){
                        request.getSession().setAttribute("user",users.get(0));
                        question.setId(id);
                        question.setCreator(users.get(0).getId());
                        question.setTitle(title);
                        question.setDescription(description);
                        question.setTag(tag);
                        questionService.questionupdate(question);
                        return "redirect:/";
                    }else {
                        model.addAttribute("error","用户未登录");
                    }
                }
            }
        }
        model.addAttribute("error","用户未登录");
        return "publish";
    }
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id")Integer id,
                       Model model){
        QuestionDTO question= questionService.revertPage(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }
}
