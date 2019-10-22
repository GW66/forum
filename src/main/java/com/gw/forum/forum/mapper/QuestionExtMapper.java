package com.gw.forum.forum.mapper;

import com.gw.forum.forum.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incComment(Question record);
    int incLike(Question record);
}