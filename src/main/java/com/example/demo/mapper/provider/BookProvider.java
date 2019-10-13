package com.example.demo.mapper.provider;

import com.example.demo.pojo.Book;
import org.apache.ibatis.jdbc.SQL;

public class BookProvider {
    /**
     * update
     * @param Book book
     * @return
     */
    public String updateSQL(final Book book) {
        return new SQL() {
            {
                UPDATE("book");
                if (book.getName() != null) {
                    SET("book_name = #{name}");
                }
                if (book.getDescribe()!=null){
                    SET("book_des = describe");
                }
                if (book.getImage()!=null){
                    SET("image = #{image}");
                }
                if (book.getNum() > 0){
                    SET("book_num = #{num}");
                }
                if (book.getValue() > 0){
                    SET("book_value = #{value}");
                }
                if (book.getLocation()!=null){
                    SET("book_locate = #{location}");
                }
                WHERE("book_id = #{id}");
            }
        }.toString();
    }
}
