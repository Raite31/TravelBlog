package cn.lvyou.lwww_springboot.mapper;

import cn.lvyou.lwww_springboot.entiy.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    /**
     * 通过文章ID查询对应评论信息
     * @param id
     * @return
     */
    List<Comment> queryListByArtId(@Param("id") Integer id, @Param("type") String type);

    /**
     * 插入评论
     * @param comment 评论信息
     * @return 受影响行数
     */
    int insertComment(Comment comment);

    /**
     * 通过文章id查询文章评论数量
     * @param artid
     * @return
     */
    int queryCountByArtId(@Param("artId") Integer artid);
}
