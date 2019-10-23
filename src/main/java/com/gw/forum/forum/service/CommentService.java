package com.gw.forum.forum.service;

import com.gw.forum.forum.dto.CommentCreateDTO;
import com.gw.forum.forum.dto.CommentDTO;
import com.gw.forum.forum.enums.CommentTypeEnum;
import com.gw.forum.forum.exception.CustomizaErrorCode;
import com.gw.forum.forum.exception.CustomizeException;
import com.gw.forum.forum.mapper.CommentMapper;
import com.gw.forum.forum.mapper.QuestionMapper;
import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;
    @Transactional
    public void insert(CommentCreateDTO commentCreateDTO, User user) {
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setLikeCount(0L);
        comment.setCommentator(user.getId());
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

    public List<CommentDTO> listByQuestionId(Long id) {
//        List<CommentDTO> commentDTOList=new ArrayList<>();
//        CommentExample commentExample = new CommentExample();
//        commentExample.createCriteria()
//                .andParentIdEqualTo(id).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
//        List<Comment> commentList = commentMapper.selectByExample(commentExample);
//        for (Comment comment:commentList){
//            CommentDTO commentDTO=new CommentDTO();
//            User user = userMapper.selectByPrimaryKey(comment.getCommentator());
//            BeanUtils.copyProperties(comment,commentDTO);
//            commentDTO.setUser(user);
//            commentDTOList.add(commentDTO);
//        }
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        if (commentList.size()==0){
            return null;
        }
        Set<Long> commentators = commentList.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds=new ArrayList<>();
        userIds.addAll(commentators);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOList = commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOList;
    }
}
