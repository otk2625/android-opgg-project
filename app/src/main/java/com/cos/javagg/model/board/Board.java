package com.cos.javagg.model.board;

import com.cos.javagg.model.like.Likes;
import com.cos.javagg.model.reply.Reply;
import com.cos.javagg.model.user.User;

import java.sql.Timestamp;
import java.util.List;

import kotlin.jvm.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
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

    private List<Likes> likes; //A이미지에 홍길동, 장보고, 임꺽정 좋아요. (고소영)

    private boolean likeState;

    private Timestamp createDate;
}
