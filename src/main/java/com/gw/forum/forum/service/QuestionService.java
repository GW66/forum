package com.gw.forum.forum.service;

import com.gw.forum.forum.dto.PaginationDTO;
import com.gw.forum.forum.dto.QuestionDTO;
import com.gw.forum.forum.exception.CustomizaErrorCode;
import com.gw.forum.forum.exception.CustomizeException;
import com.gw.forum.forum.mapper.QuestionExtMapper;
import com.gw.forum.forum.mapper.QuestionMapper;
import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.Question;
import com.gw.forum.forum.model.QuestionExample;
import com.gw.forum.forum.model.User;
import org.apache.ibatis.session.RowBounds;
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
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    public List<QuestionDTO> listpage(Integer page,Integer size){
        Integer startSize=(page-1)*size;
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(startSize, size));
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for(Question question:questionList){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
    public PaginationDTO showpage(Integer page, Integer size){
        Integer totalCount=(int) questionMapper.countByExample(new QuestionExample());
        PaginationDTO paginationDTO=new PaginationDTO(page,totalCount,size);
        return paginationDTO;
    }

    public List<QuestionDTO> listpage(Integer creator,Integer page,Integer size){
        Integer startSize=(page-1)*size;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(creator);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(startSize, size));
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for(Question question:questionList){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
    public PaginationDTO showpage(Integer creator,Integer page, Integer size){
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(creator);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        PaginationDTO paginationDTO=new PaginationDTO(page,totalCount,size);
        return paginationDTO;
    }

    public QuestionDTO revertPage(Integer id){
        Question question=questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new CustomizeException(CustomizaErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }
    public void questionupdate(Question question){
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            question.setGmtCreate(question.getGmtCreate());
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            int update=questionMapper.updateByExampleSelective(question, questionExample);
            if (update!=1){
                throw new CustomizeException(CustomizaErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Integer id) {
        Question question=new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}
