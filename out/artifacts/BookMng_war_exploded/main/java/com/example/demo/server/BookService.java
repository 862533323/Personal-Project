package com.example.demo.server;

import com.example.demo.mapper.BookMapper;
import com.example.demo.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BookService {
    int size=10;
    @Autowired
    BookMapper mapper;
    public Book selectBookById(int id){
        return mapper.selectBookById(id);
    }
    public List<Book> selectByKeyword(String keyword,int page){
        String s="%"+keyword+"%";
        List<Book> back=mapper.selectByKeyWord(s,page*size,(page+1)*size);
        System.out.println("搜索关键字["+keyword+"],共"+back.size()+"条记录！");
        if (back.size()==0)
            return null;
        return back;
    }
    public List<Book> selectByType(String id,int type,int page){
        List<Book> list=mapper.selectByType(id,type,page*size,(page+1)*size);
        System.out.println(id+":search [keyword]:"+type+",[result]________size:"+list.size());
        return list;
    }
    public List<Book> recommend(String location,int page){
        return mapper.recommend("%"+location.substring(0,2)+"%",page*size,(page+1)*size);
    }
    public void addBook(Book book){
        mapper.addBook(book);
    }
    public void updateBook(Book book){
        mapper.updateBook(book);
    }
    public int[] selectType(String uid,int bid){
        return mapper.selectType(uid,bid);
    }
    public void deleteByType(String uid,int bid,int type){
        mapper.deleteByType(uid,bid,type);
    }
    public void insertByType(String uid, int bid, int type, Timestamp time){
        mapper.insertByType(uid,bid,type,time);
    }
}
