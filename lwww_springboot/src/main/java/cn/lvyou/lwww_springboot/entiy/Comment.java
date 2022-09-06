package cn.lvyou.lwww_springboot.entiy;

import lombok.Data;

import java.util.Date;
@Data
public class Comment {
    private Integer id;
    private String content;
    private Integer uid;
    private Integer artId;
    private String time;
    private String type;
    private User user; // 评论人的信息
}
