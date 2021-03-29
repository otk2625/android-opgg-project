package com.cos.javagg.model.board;

import com.cos.javagg.model.reply.Reply;
import com.cos.javagg.model.user.User;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
    private Integer id;

    private String communityType;


    private String title;

    private String content;

    private int readCount;

    private int likeCount;

    private int dislikeCount;

    private User user;

    private List<Reply> replys;

    private Timestamp createDate;
}
