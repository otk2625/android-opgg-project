package com.cos.javagg.model.like;

import com.cos.javagg.model.board.Board;
import com.cos.javagg.model.user.User;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Likes {

    private int id;

    private Board board;

    private User user;

    private Timestamp createDate;

    private boolean isLike;
}
