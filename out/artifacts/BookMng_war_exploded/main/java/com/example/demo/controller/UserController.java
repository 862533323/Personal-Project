package com.example.demo.controller;


import com.example.demo.pojo.Book;
import com.example.demo.pojo.User;
import com.example.demo.server.BookService;
import com.example.demo.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {


    public static final int Love=0;
    public static final int Lend=1;
    public static final int Tips=2;
    public static final int Recommend=3;

    @Autowired
    UserService service;
    @Autowired
    BookService bookService;

    @ResponseBody
    @RequestMapping("/unLogin")
    void unLogin(HttpServletRequest request){
        System.out.println("unLogin!");
        HttpSession session=request.getSession();
        session.invalidate();
    }

    @ResponseBody
    @RequestMapping("/register/{id}/{pwd}")
    String register(@PathVariable String id,@PathVariable String pwd){

        User user1=service.selectUser(id);
        if (user1!=null){
            return "用户名已注册";
        }
        user1=new User(id,pwd,0,"");
        service.insertUser(user1);
        return "success";
    }


    @ResponseBody
    @RequestMapping("/changeLove/{id}")
    String love(@PathVariable int id,HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user1 = (User) session.getAttribute("user");
        if (user1==null)
            return "需要登录";
        int list[] = bookService.selectType(user1.getNum(), id);
        for (int i : list) {
            if (i == 0) {
                bookService.deleteByType(user1.getNum(), id, 0);
                return "delete";
            }
        }
        bookService.insertByType(user1.getNum(), id, 0, AndroidController.getTime());
        return "success";
    }

    @RequestMapping("/getSetting")
    @ResponseBody
    String getSetting(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("user");
        if (user==null)
            return "<ul><li><a href='/login.html'>去登陆<a/></li></ul>\n" +
                    "                <p>hard working,just for you</p>";
        String back="\n" +
                "                <ul>\n" +
                "                    <li>\n" +
                "                        <a href='#' onclick='love()'>我的收藏</a>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <a href='#' onclick='lend()'>我的借阅</a>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <a href=\"/user/mainPage.html\">我的首页</a>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <a href=\"#\" onclick='unLogin()'>退出登录</a>\n" +
                "                    </li>\n";
        if (user.getPower()>5)
            back+="                    <li>\n" +
                    "                        <a href='/manager/upload.html'>管理员拓展功能</a>\n" +
                    "                    </li></ul>"+
                    "                <p>hard working,just for you</p>"+"<li><a href='#'>about us</a></li>";
        else
            back+="</ul><p>hard working,just for you</p><li><a href='/model/about.html'>about us</a></li>";
            return back;
    }
    @RequestMapping("/book_inf/{id}")
    String book_inf(Model model,@PathVariable int id){
        System.out.println("book inf");
        Book book=bookService.selectBookById(id);
        model.addAttribute("name",book.getName());
        model.addAttribute("num",book.getNum());
        model.addAttribute("price",book.getValue());
        model.addAttribute("describe",book.getDescribe());
        model.addAttribute("image",book.getImage());
        return "/book/inf";
    }

    @RequestMapping("/getUserName")
    @ResponseBody
    String name(HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("user");
        if (user==null)
            return null;
        return user.getName();
    }
    @RequestMapping("/to_home")
    String home(@Nullable String id){
        if (id!=null)
            System.out.println("to "+id+"`s home!");
        return "/user/mainPage";
    }
    @RequestMapping("/search/{keyword}")
    @ResponseBody
    String[] getBook(HttpServletRequest request,@PathVariable String keyword){
        HttpSession session=request.getSession();
        session.setAttribute("page",0);
        session.setAttribute("keyword",keyword);
        String id=(String) session.getAttribute("user_id");
        if (keyword.equals("null")){
            System.out.println("New enter");
            session.setAttribute("keyword","%");
            return toStrings(bookService.selectByKeyword("",0));
        }else return search(id,keyword,0);
    }

    @RequestMapping("/searchNext")
    @ResponseBody
    String[] next(HttpServletRequest request,Model model){
        HttpSession session=request.getSession();
        int page= (int) session.getAttribute("page");
        String keyword= (String) session.getAttribute("keyword");
        String id=(String) session.getAttribute("user_name");
        session.setAttribute("page",++page);
        return search(id,keyword,page);
    }
    String[] search(String id,String keyword,int page){
        if(keyword.equals("to_myLend")){
            return toStrings(bookService.selectByType(id,Lend,page));
        }else if (keyword.equals("to_myLove")){
            return toStrings(bookService.selectByType(id,Love,page));
        }else if (keyword.equals("to_tips")){
            return toStrings(bookService.selectByType(id,Tips,page));
        }else if (keyword.equals("to_recommend")){
            return toStrings(bookService.selectByType(id,Recommend,page));
        }else{
            return toStrings(bookService.selectByKeyword(keyword,page));
        }
    }
    String[] toStrings_s(List<Book> list){
        String[] back=new String[10];
        int i=0;
        if (list==null)
            return null;
        for(Book book:list){
            back[i++] = "<tr ><td>" + book.getName()+ "</td>"
                    + "<td ><div class='img-box' onclick='javascript:{inf("+book.getId()+");}'>" +"<img src='"+book.getImage() +"'> "+ "</div></td>"
                    + "<td>" + book.getDescribe() + "</td>"
                    + "<td>" + book.getNum() + "</td><tr/>";
        }
        return back;
    }
    String[] toStrings(List<Book> list){
        if (list==null)
            return null;
        String[] back=new String[list.size()];
        int i=0;
        for (Book book:list){
            back[i++]="<div class='col-md-4 col-sm-6'>" +
                    "            <div class='portfolio-item'>" +
                    "                <div class='thumb'>" +
                    "                    <a href='"+book.getImage()+"' data-lightbox='image-1'><div class='hover-effect'>" +
                    "                        <div class='hover-content' >" +
                    "                            <h1 style='color:gold;'>"+book.getName()+"</h1>" +
                    "                            <p style='color:gold;'>"+book.getDescribe().substring(0,20>book.getDescribe().length()?book.getDescribe().length():20)+"..."+"</p>" +
                    "                        </div>" +
                    "                    </div></a>" +
                    "                    <div class='image' onclick='loveIt(+"+book.getId()+")' >" +
                    "                        <img src='"+book.getImage()+"'>" +
                    "                    </div>" +
                    "                </div>" +
                    "            </div>" +
                    "        </div>";
        }
        return back;
    }
}
