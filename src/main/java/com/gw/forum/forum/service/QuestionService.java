package com.gw.forum.forum.service;

import com.gw.forum.forum.dto.PaginationDTO;
import com.gw.forum.forum.dto.QuestionDTO;
import com.gw.forum.forum.mapper.QuestionMapper;
import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.Question;
import com.gw.forum.forum.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> listpage(Integer page,Integer size){
        Integer startSize=(page-1)*size;
        List<Question> questionList=questionMapper.listpage(startSize,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for(Question question:questionList){
            User user=userMapper.findbyId(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
    public PaginationDTO showpage(Integer page, Integer size){
        Integer totalCount=questionMapper.totalCount();
        PaginationDTO paginationDTO=new PaginationDTO(page,totalCount,size);
        return paginationDTO;
    }
}
