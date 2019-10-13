package com.example.demo.mapper;

import com.example.demo.mapper.provider.BookProvider;
import com.example.demo.pojo.Book;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BookMapper {
    @Select("select * from book where book_id=#{id}")
    Book selectBookById(int id);

    @Select("select book_id,book_name,book_locate,book_value,book_num,book_des,image " +
            "from book where book_name like #{keyword} or book_des like #{keyword} limit #{begin},#{end}")
    List<Book> selectByKeyWord(String keyword,int begin,int end);

    @Select("select * from book where book_id in (select distinct book_id from information where " +
            "user_id=#{id} and type=#{type}) limit #{begin},#{end}")
    List<Book> selectByType(String id,int type,int begin,int end);
    @Select("select * from book where book_locate like #{id} limit #{begin},#{end}")
    List<Book> recommend(String location,int begin,int end);
    @Insert("insert into book(book_name,book_num,book_locate,book_value,book_des,image) values(#{name},#{num},#{location},#{value},#{describe},#{image})")
    void addBook(Book book);
    @Update("update book set book_name=#{name},book_num=#{num},book_locate=#{location},image=#{image} where book_id=#{id}")
    void updateBook(Book book);
    @Select("select type from information where user_id=#{uid} and book_id=${bid}")
    int[] selectType(String uid,int bid);
    @Delete("delete from information where user_id=#{uid} and book_id=#{bid} and type=#{type}")
    void deleteByType(String uid,int bid,int type);
    @Insert("insert into information(user_id,book_id,type,time) value(#{uid},#{bid},#{type},#{time})")
    void insertByType(String uid, int bid, int type, Timestamp time);

    /**
     * update
     * @param Book book
     * @return
     */
    @UpdateProvider(type = BookProvider.class, method = "updateSQL")
    void updateTest(Book book);
}
