package com.gw.forum.forum.mapper;

import com.gw.forum.forum.dto.QuestionQueryDTO;
import com.gw.forum.forum.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int incComment(Question record);
    int incLike(Question record);
    List<Question> selectRegexp (Question record);
    Integer countBySearch(QuestionQueryDTO questionQueryDTO);
    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}