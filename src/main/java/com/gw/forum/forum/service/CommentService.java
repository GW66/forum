package com.gw.forum.forum.service;

import com.gw.forum.forum.dto.CommentDTO;
import com.gw.forum.forum.enums.CommentTypeEnum;
import com.gw.forum.forum.exception.CustomizaErrorCode;
import com.gw.forum.forum.exception.CustomizeException;
import com.gw.forum.forum.mapper.CommentMapper;
import com.gw.forum.forum.mapper.QuestionExtMapper;
import com.gw.forum.forum.mapper.QuestionMapper;
import com.gw.forum.forum.model.Comment;
import com.gw.forum.forum.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @Transactional
    public void insert(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setLikeCount(0L);
        comment.setCommentator(1L);
        if (comment.getParentId()==null||comment.getParentId()==0){
           throw new CustomizeException(CustomizaErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType()==null||!CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizaErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType()==CommentTypeEnum.QUESTION.getType()){
//            回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question==null){
                throw new CustomizeException(CustomizaErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionService.incComment(question.getId());
        } else {
//            回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment==null){
                throw new CustomizeException(CustomizaErrorCode.COMMENT_NOT_FOUND);
            }
        }
    }
}
