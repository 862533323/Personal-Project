package com.example.demo.server;

import com.example.demo.pojo.Sit;
import com.example.demo.pojo.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper mapper;
    public User selectUser(String id){
        System.out.println("server id:"+id);
        return mapper.selectUser(id);
    }
    public List<User> select_users(){
        return mapper.select_users();
    }
    public List<Sit> selectSit(){
        return mapper.selectSit();
    }
    public void insertSit(String id,String location){
        mapper.insertSit(id,location);
    }
    public String selectSitById(String location){
        return mapper.selectSitById(location);
    }
    public void deleteSitById(String id){
        mapper.deleteSitById(id);
    }
    public void insertUser(User user){
        mapper.insertUser(user.getNum(),user.getPassword(),0,"新用户");
    }
}
