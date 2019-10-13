package com.example.demo;

import com.example.demo.pojo.Book;
import com.example.demo.server.BookService;
import com.example.demo.server.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    UserService service;

    @Autowired
    BookService bookService;
    @Test
    public void contextLoads() {
    }

    @Test
    public void test(){
        System.out.println(service.selectUser("12"));
        for (Book book:bookService.selectByKeyword("",0)){
            System.out.println(book.toString());
        }
    }

}

