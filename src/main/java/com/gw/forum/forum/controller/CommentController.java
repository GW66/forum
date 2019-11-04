package com.gw.forum.forum.controller;
/**
 *用于添加回复数据
 */
import com.gw.forum.forum.dto.CommentCreateDTO;
import com.gw.forum.forum.dto.CommentDTO;
import com.gw.forum.forum.dto.ResultDTO;
import com.gw.forum.forum.enums.CommentTypeEnum;
import com.gw.forum.forum.exception.CustomizaErrorCode;
import com.gw.forum.forum.exception.CustomizeException;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.service.CommentService;
import com.gw.forum.forum.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    /**
     *用于添加回复数据
     */
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            throw new CustomizeException(CustomizaErrorCode.NO_LOGIN);
        }
//        if (commentCreateDTO.getContent()==null||commentCreateDTO==null || commentCreateDTO.getContent()==""){
        if (commentCreateDTO.getContent()==null||StringUtils.isBlank(commentCreateDTO.getContent())){
            throw new CustomizeException(CustomizaErrorCode.COMMENT_IS_EMPTY);
        }
        commentService.insert(commentCreateDTO,user);
        return ResultDTO.errorOf(CustomizaErrorCode.REQUEST_OK);
    }
    /**
     *用于加载二级评论
     */
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comment(@PathVariable("id") Long id){
        List<CommentDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.data(commentDTOList);
    }
}
