package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.CommentDTO;
import com.gw.forum.forum.dto.QuestionDTO;
import com.gw.forum.forum.enums.CommentTypeEnum;
import com.gw.forum.forum.service.CommentService;
import com.gw.forum.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RevertController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/revert/{id}")
    public String revert(@PathVariable("id") Long id,
                         Model model){
        QuestionDTO questionDTO=questionService.revertPage(id);
        List<QuestionDTO> questionDTOTagList=questionService.selectRegexp(questionDTO);
        questionService.incView(id);
        List<CommentDTO> commentDTOList=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("commentDTOList",commentDTOList);
        model.addAttribute("questionDTOTagList",questionDTOTagList);
        return "revert";
    }
}
