package com.gw.forum.forum.mapper;

import com.gw.forum.forum.model.Comment;

public interface CommentExtMapper {
    int incComment(Comment record);
    int incLike(Comment record);
}