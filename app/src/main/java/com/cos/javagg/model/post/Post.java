package com.cos.javagg.model.post;

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
public class Post {
    private Integer id;
    private String title;
    private String content;
    private User user;
    private int likeCount;
    private int viewCount;
    private List<Reply> replys;
    private Timestamp createDate;
}
