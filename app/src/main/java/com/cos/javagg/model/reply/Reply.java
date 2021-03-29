package com.cos.javagg.model.reply;

import com.cos.javagg.model.board.Board;
import com.cos.javagg.model.user.User;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reply {

    private Integer id;

    private String content;

    private User user;

    private Board board;

    private int likeCount;

    private Timestamp createDate;
}
