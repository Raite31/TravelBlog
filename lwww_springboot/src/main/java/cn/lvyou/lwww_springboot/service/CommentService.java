package cn.lvyou.lwww_springboot.service;

import cn.lvyou.lwww_springboot.entiy.Comment;

public interface CommentService {
    /**
     * 插入评论
     * @param comment 评论信息
     * @return 受影响行数
     */
    int insertComment(Comment comment);
}
