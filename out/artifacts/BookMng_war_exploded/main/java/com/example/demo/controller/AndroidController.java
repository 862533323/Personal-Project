package com.example.demo.controller;

import com.example.demo.pojo.Book;
import com.example.demo.pojo.Sit;
import com.example.demo.pojo.User;
import com.example.demo.server.BookService;
import com.example.demo.server.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/android")
public class AndroidController {


    public static final int Love=0;
    public static final int Lend=1;
    public static final int Tips=2;
    public static final int Recommend=3;

    final Gson gson=new Gson();

    @Autowired
    BookService service;

    @Autowired
    UserService userService;


    @ResponseBody
    @RequestMapping("/setSit/{i}/{j}")
    String setSit(HttpServletRequest request,@PathVariable int i,@PathVariable int j){
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("user");
        if (user!=null){
            int[] sit=new int[2];
            sit[0]=i;
            sit[1]=j;
            String id=userService.selectSitById(gson.toJson(sit));
            if (id!=null){
                if (id.equals(user.getNum())){
                    userService.deleteSitById(user.getNum());
                    return "delete";
                }
                return "false";
            }
            userService.deleteSitById(user.getNum());
            userService.insertSit(user.getNum(),gson.toJson(sit));
            return "success";
        }
        return "login";
    }

    @ResponseBody
    @RequestMapping("/sit")
    String sit(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("user");
        List<Sit> sits=userService.selectSit();
        if (user!=null){
            String id=user.getNum();
            int[][] back=new int[4][100];
            for (int i=0;i<4;i++){
                for (int j=0;j<100;j++){
                    back[i][j]=0;
                }
            }
            for (Sit sit:sits){
                int[] location=gson.fromJson(sit.getLocation(),new TypeToken<int[]>(){}.getType());
                if (sit.getUid().equals(id)){
                    back[location[0]][location[1]]=-1;
                }else {
                    back[location[0]][location[1]]=1;
                    System.out.println(sit.getLocation());
                }
            }
            System.out.println(user+" || success");
            return gson.toJson(back);
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/isLove/{id}")
    String isLove(@PathVariable int id,HttpServletRequest request){
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("user");
        int list[]=service.selectType(user.getNum(),id);
        for (int i:list){
            if (i==0){
                return "love";
            }
        }
        return "null";
    }
    @ResponseBody
    @RequestMapping("/changeLove/{id}")
    String love(@PathVariable int id,HttpServletRequest request){
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("user");
        int list[]=service.selectType(user.getNum(),id);
        for (int i:list){
            if (i==0){
                service.deleteByType(user.getNum(),id,0);
                return "success";
            }
        }
        service.insertByType(user.getNum(),id,0,getTime());
        return "success";
    }
    @ResponseBody
    @RequestMapping("/login/{name}/{pwd}")
    String login(@PathVariable String name,@PathVariable String pwd, HttpServletRequest request){
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("user");
        if (user!=null)
            return user.getName();
        System.out.println("name:"+name+",pwd:"+pwd+" login!");
        user =userService.selectUser(name);
        if (user==null)
            return "false";
        if (pwd.equals(user.getPassword())){
            session.setAttribute("user",user);
            session.setAttribute("user_id",name);
            session.setAttribute("page",0);
            session.setAttribute("keyword","%");
            return user.getName();
        }else{
            return "false";
        }
    }
    @RequestMapping("/search/{keyword}/{page}")
    @ResponseBody
    String getBook(HttpServletRequest request, @PathVariable String keyword, @PathVariable int page){
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("user");
        System.out.println("___________________________________搜索||[user]|"+user);
        String id=(String) session.getAttribute("user_id");
        session.setAttribute("keyword",keyword);
        if (keyword.equals("null")){
            session.setAttribute("keyword","%");
            return toStrings(service.selectByKeyword("",page));
        }else return search(id,keyword,page);
    }

    @RequestMapping("/searchNext")
    @ResponseBody
    String next(HttpServletRequest request){
        HttpSession session=request.getSession();
        int page;
        try{
            page= (int) session.getAttribute("page");
        }catch (Exception e){
            return null;
        }
        String id=(String) session.getAttribute("user_name");
        String keyword= (String) session.getAttribute("keyword");
        System.out.println("next|"+page);
        session.setAttribute("page",++page);
        return search(id,keyword,page);

    }

    String search(String id,String keyword,int page){
        System.out.println("[keyword]|"+keyword+";[page]|"+page);
        if(keyword.equals("lend")){
            return toStrings(service.selectByType(id,Lend,page));
        }else if (keyword.equals("love")){
            return toStrings(service.selectByType(id,Love,page));
        }else if (keyword.equals("tips")){
            return toStrings(service.selectByType(id,Tips,page));
        }else if (keyword.equals("recommend")){
            return toStrings(service.selectByType(id,Recommend,page));
        }else{
            return toStrings(service.selectByKeyword(keyword,page));
        }
    }
    String toStrings(List<Book> list){
        String back;
        int i=0;
        if (list==null)
            return null;
        back=gson.toJson(list);
        return back;
    }

    public static Timestamp getTime(){
        Timestamp timestamp = new Timestamp(new Date().getTime()); //2013-01-14 22:45:36.484   // new Date()为获取当前系统时间
        return timestamp;
    }
}
