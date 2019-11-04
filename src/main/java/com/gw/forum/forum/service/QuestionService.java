package com.gw.forum.forum.service;

import com.gw.forum.forum.dto.PaginationDTO;
import com.gw.forum.forum.dto.QuestionDTO;
import com.gw.forum.forum.dto.QuestionQueryDTO;
import com.gw.forum.forum.exception.CustomizaErrorCode;
import com.gw.forum.forum.exception.CustomizeException;
import com.gw.forum.forum.mapper.QuestionExtMapper;
import com.gw.forum.forum.mapper.QuestionMapper;
import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.Question;
import com.gw.forum.forum.model.QuestionExample;
import com.gw.forum.forum.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    public List<QuestionDTO> listpage(String search,Integer page,Integer size){
//        用于页面搜索
        if (StringUtils.isNotBlank(search)){
//        String stringTag=tag.replaceAll(",","|");
            String[] tags= StringUtils.split(search," ");
            search=Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        Integer startSize=(page-1)*size;
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        if (search==""){
            search=null;
        }
        questionQueryDTO.setSearch(search);
        questionQueryDTO.setPage(startSize);
        questionQueryDTO.setSize(size);
        List<Question> questionList = questionExtMapper.selectBySearch(questionQueryDTO);
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
    public PaginationDTO showpage(String search,Integer page, Integer size){
        if (StringUtils.isNotBlank(search)){
//        String stringTag=tag.replaceAll(",","|");
            String[] tags= StringUtils.split(search," ");
            search=Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount=(int) questionExtMapper.countBySearch(questionQueryDTO);
        PaginationDTO paginationDTO=new PaginationDTO(page,totalCount,size);
        return paginationDTO;
    }

    public List<QuestionDTO> listpage(Long creator, Integer page, Integer size){
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
    public PaginationDTO showpage(Long creator, Integer page, Integer size){
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(creator);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        PaginationDTO paginationDTO=new PaginationDTO(page,totalCount,size);
        return paginationDTO;
    }

    public QuestionDTO revertPage(Long id){
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
            questionMapper.insertSelective(question);
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

    public void incView(Long id) {
        Question question=new Question();
        question.setId(id);
        question.setViewCount(1L);
        questionExtMapper.incView(question);
    }
    public void incComment(Long id) {
        Question question=new Question();
        question.setId(id);
        question.setCommentCount(1L);
        questionExtMapper.incComment(question);
    }
    public void incLike(Long id) {
        Question question=new Question();
        question.setId(id);
        question.setLikeCount(1L);
        questionExtMapper.incLike(question);
    }

    public List<QuestionDTO> selectRegexp(QuestionDTO questionDTO) {
        String tag=questionDTO.getTag();
        if (StringUtils.isBlank(tag)){
            return null;
        }
//        String stringTag=tag.replaceAll(",","|");
        String[] tags= StringUtils.split(tag,",");
        String stringTag=Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question=new Question();
        question.setId(questionDTO.getId());
        question.setTag(stringTag);
        List<Question> questionList = questionExtMapper.selectRegexp(question);
        List<QuestionDTO> questionDTOList = questionList.stream().map(question1 -> {
            QuestionDTO questionDTO1=new QuestionDTO();
            BeanUtils.copyProperties(question1,questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());
        return questionDTOList;
    }
}
