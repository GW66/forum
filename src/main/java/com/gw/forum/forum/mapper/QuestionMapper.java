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
//    所有问题
    @Select("select *from question LIMIT #{startSize},#{size}")
    List<Question> listpage(@Param("startSize") Integer startSize,@Param("size") Integer size);
    @Select("select count(*)from question")
    Integer totalCount();
//  用户问题
    @Select("select *from question where creator=#{creator} LIMIT #{startSize},#{size}")
    List<Question> selflistpage(@Param("creator") Integer creator,@Param("startSize") Integer startSize,@Param("size") Integer size);
    @Select("select count(*)from question where creator=#{creator}")
    Integer selftotalCount(@Param("creator") Integer creator);
    @Select("select *from question where id=#{id}")
    Question revertPage(@Param("id") Integer id);
}
