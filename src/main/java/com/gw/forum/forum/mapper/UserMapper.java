package com.gw.forum.forum.mapper;

import com.gw.forum.forum.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified) values(#{accountid},#{name},#{token},#{gmtcreate},#{gmtmodified})")
    void insert(User user);
    @Select("select *from user where token=#{token}")
    User findbytoken(@Param("token")String token);
}
