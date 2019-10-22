package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.CommentDTO;
import com.gw.forum.forum.dto.ResultDTO;
import com.gw.forum.forum.exception.CustomizaErrorCode;
import com.gw.forum.forum.exception.CustomizeException;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.service.CommentService;
import com.gw.forum.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private QuestionService questionService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            throw new CustomizeException(CustomizaErrorCode.NO_LOGIN);
        }
        commentService.insert(commentDTO);
        return ResultDTO.errorOf(CustomizaErrorCode.REQUEST_OK);
    }
}
