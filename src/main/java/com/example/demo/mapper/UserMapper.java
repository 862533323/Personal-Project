package com.example.demo.mapper;

import com.example.demo.pojo.Sit;
import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    @Select("insert into user(num,password,power,name) value(#{a},#{b},#{c},#{d})")
    void insertUser(String a,String b,int c,String d );
    @Select("SELECT * FROM user WHERE num = #{id}")
    User selectUser(String id);
    @Select("SELECT * FROM user")
    List<User> select_users();
    @Select("select * from sit")
    List<Sit> selectSit();
    @Insert("insert into sit(uid,location) values(#{id},#{location})")
    void insertSit(String id,String location);

    @Select("select uid from sit where location=#{location}")
    String selectSitById(String location);
    @Delete("delete from sit where uid=#{id}")
    void deleteSitById(String id);
}
