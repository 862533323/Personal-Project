package com.example.demo.controller;

import com.example.demo.pojo.Book;
import com.example.demo.pojo.User;
import com.example.demo.server.BookService;
import com.example.demo.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/mng")
public class ManagerController {
    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    String Chk(User user,String uid,int bookId){
        if (user==null)
            return "管理员未登录";
        if (user.getPower()<5){
            return "需要一个管理员账号";
        }
        if (userService.selectUser(uid)==null){
            return "用户id不存在";
        }
        if (bookService.selectBookById(bookId)==null){
            return "图书号不存在";
        }
        return "true";
    }
    @ResponseBody
    @RequestMapping("/removeLend")
    public String removeLend(String uid,int bookId,HttpServletRequest request){
        User user=(User) request.getSession().getAttribute("user");
        if (!Chk(user,uid,bookId).equals("true"))
            return Chk(user,uid,bookId);
        bookService.deleteByType(uid,bookId,1);
        return "success";
    }
    @ResponseBody
    @RequestMapping("/addLend")
    public String addLend(String uid,int bookId,HttpServletRequest request){
        User user=(User) request.getSession().getAttribute("user");
        if (!Chk(user,uid,bookId).equals("true"))
            return Chk(user,uid,bookId);
        int[] back=bookService.selectType(uid,bookId);
        for (int i:back){
            if (i==1){
                return "信息已经存在";
            }
        }
        bookService.insertByType(uid,bookId,1,AndroidController.getTime());
        return "success";
    }

    @ResponseBody
    @RequestMapping("/addBook")
    public String addBook(HttpServletRequest request){
        User user=(User) request.getSession().getAttribute("user");
        if (user==null)
            return "/Login/to_login";
        if (user.getPower()<5){
            return "low";
        }
        return "/mng/upload";
    }
    @RequestMapping("/upload")
    String upload(){
        return "/manager/upload";
    }
    /**
     * 实现单文件上传
     *http://ip:port/fileUpload
     * */
    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("fileName") MultipartFile file, String location, String describe, String name, int num, int price, HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("user");
        if (user==null||user.getPower()<5){
            return "login";
        }
        Book book=new Book(name,num,location,price,describe,saveFile(file));
        bookService.addBook(book);
        return "/manager/upload";
    }
    String saveFile(MultipartFile file){
        if(file.isEmpty()){;
            return "/BookMng/image/demo.jpg";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = "C:/BookMng/image" ;//文件保存路径
        File targetFile = new File(path + "/" + fileName);
        if(!targetFile.getParentFile().exists()){ //判断文件父目录是否存在
            targetFile.getParentFile().mkdir();
        }
        try {
            file.transferTo(targetFile); //保存文件
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "/BookMng/image/demo.jpg";
        }
        return "/BookMng/image"+ "/" + fileName;
    }
}
