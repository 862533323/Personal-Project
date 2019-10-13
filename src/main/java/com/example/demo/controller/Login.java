package com.example.demo.controller;

import com.example.demo.pojo.User;
import com.example.demo.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/Login")
public class Login {

    @Autowired
    UserService service;

    @RequestMapping("/index")
    String index(){
        return "model/index";
    }
    @RequestMapping("/to_login")
    String to_login(){
        return "login";
    }
    @ResponseBody
    @RequestMapping("/login")
    String login(String name, String pwd, HttpServletRequest request){
        System.out.println("name:"+name+",pwd:"+pwd);
        HttpSession session=request.getSession();
        session.setAttribute("user",service.selectUser(name));
        session.setAttribute("user_id",name);
        session.setAttribute("page",0);
        session.setAttribute("keyword","%");
        User user =service.selectUser(name);
        if (user==null)
            return "false";
        if (pwd.equals(user.getPassword())){
            return "/user/to_home?id="+name;
        }else{
            return "false";
        }
    }
    @RequestMapping("/to_register")
    String to_register(){
        return "register";
    }

}
