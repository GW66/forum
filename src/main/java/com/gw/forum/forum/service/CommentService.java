package com.gw.forum.forum.service;

import com.gw.forum.forum.dto.CommentCreateDTO;
import com.gw.forum.forum.dto.CommentDTO;
import com.gw.forum.forum.enums.CommentTypeEnum;
import com.gw.forum.forum.enums.NotificationEnum;
import com.gw.forum.forum.enums.NotificationStatusEnum;
import com.gw.forum.forum.exception.CustomizaErrorCode;
import com.gw.forum.forum.exception.CustomizeException;
import com.gw.forum.forum.mapper.*;
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
    private CommentExtMapper commentExtMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Transactional
//    添加回复信息
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
            commentMapper.insertSelective(comment);
            questionService.incComment(question.getId());
            createNotify(user, question.getTitle(), question.getCreator(),NotificationEnum.REPLY_QUESTION,question.getId());
        } else {
//            回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (dbComment==null){
                throw new CustomizeException(CustomizaErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            commentService.incComment(comment.getParentId());
            createNotify(user, dbComment.getContent(), dbComment.getCommentator(),NotificationEnum.REPLY_COMMENT,question.getId());
        }
    }

    private void createNotify(User user, String outerTitle, Long receiver,NotificationEnum notificationEnum,Long outerid) {
        if (user.getId()==receiver){
            return;
        }
        Notification notification=new Notification();
//            用户id
        notification.setNotifier(user.getId());
//            用户姓名
        notification.setNotifierName(user.getName());
//            回复id
        notification.setOuterid(outerid);
//            回复对象
        notification.setOuterTitle(outerTitle);
//            被回复人
        notification.setReceiver(receiver);
//            显示格式
        notification.setType(notificationEnum.getStatus());
//            读取状态
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
//            回复时间
        notification.setGmtCreate(System.currentTimeMillis());
        notificationMapper.insertSelective(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum commentTypeEnum) {
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
                .andParentIdEqualTo(id).andTypeEqualTo(commentTypeEnum.getType());
        commentExample.setOrderByClause("gmt_create desc");
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
    public void incComment(Long id) {
        Comment comment=new Comment();
        comment.setId(id);
        comment.setCommentCount(1L);
        commentExtMapper.incComment(comment);
    }
    public void incLike(Long id) {
        Comment comment=new Comment();
        comment.setId(id);
        comment.setLikeCount(1L);
        commentExtMapper.incLike(comment);
    }
}
