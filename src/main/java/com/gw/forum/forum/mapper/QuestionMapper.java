package com.gw.forum.forum.mapper;

import com.gw.forum.forum.dto.PaginationDTO;
import com.gw.forum.forum.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    void create(Question question);
//    @Select("select * from question")
//    List<Question> list();
    @Select("select *from question LIMIT #{startSize},#{size}")
    List<Question> listpage(@Param("startSize") Integer startSize,@Param("size") Integer size);
    @Select("select count(*)from question")
    Integer totalCount();
}
