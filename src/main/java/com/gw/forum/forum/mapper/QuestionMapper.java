package com.gw.forum.forum.mapper;

import com.gw.forum.forum.dto.PaginationDTO;
import com.gw.forum.forum.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    void insert(Question question);
    @Update("update question set title=#{title},description=#{description},gmt_create=#{gmt_create},gmt_modified=#{gmt_modified},creator=#{creator},tag=#{tag} where id=#{id}")
    void update(Question question);
    @Delete("delete from question where id=#{id}")
    void  delete(@Param("id") Integer id);

//    查询语句
//    非条件查询
//    查询部分信息
    @Select("select *from question LIMIT #{startSize},#{size}")
    List<Question> listpage(@Param("startSize") Integer startSize,@Param("size") Integer size);
//    查询总数
    @Select("select count(*)from question")
    Integer totalCount();
//    条件查询
//    查询creator部分信息
    @Select("select *from question where creator=#{creator} LIMIT #{startSize},#{size}")
    List<Question> selflistpage(@Param("creator") Integer creator,@Param("startSize") Integer startSize,@Param("size") Integer size);
//    查询creator总数
    @Select("select count(*)from question where creator=#{creator}")
    Integer selftotalCount(@Param("creator") Integer creator);
//    用id查询
    @Select("select *from question where id=#{id}")
    Question questionbyid(@Param("id") Integer id);
}
