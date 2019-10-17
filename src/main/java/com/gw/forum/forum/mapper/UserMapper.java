package com.gw.forum.forum.mapper;

import com.gw.forum.forum.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{accountid},#{name},#{token},#{gmtcreate},#{gmtmodified},#{avatar_url})")
    void insert(User user);
    @Update("update user set name=#{name},token=#{token},gmt_create=#{gmtcreate},gmt_modified=#{gmtmodified},avatar_url=#{avatar_url} where account_id=#{accountid}")
    void update(User user);
    @Delete("delete from user where id=#{id}")
    void  delete(@Param("id") Integer id);
//    查询语句
//    非条件查询

//    条件查询
//    用token查询
    @Select("select *from user where token=#{token}")
    User findbytoken(@Param("token")String token);
//    用id查询
    @Select("select * from user where id=#{id}")
    User findbyId(Integer id);
//    用account_id查询
    @Select("select *from user where account_id=#{account_id}")
    User findbyaccountid(@Param("account_id") String account_id);

}
