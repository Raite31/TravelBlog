package cn.lvyou.lwww_springboot.service.impl;

import cn.lvyou.lwww_springboot.entiy.Comment;
import cn.lvyou.lwww_springboot.mapper.CommentMapper;
import cn.lvyou.lwww_springboot.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    CommentMapper commentMapper;
    @Override
    public int insertComment(Comment comment) {
//        准备评论时间
        Date date = new Date();
        // 格式化日期和时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
//        格式化模板
        simpleDateFormat.applyPattern("yyyy-MM-DD HH:mm:ss");
//        开始格式化
        String newDate = simpleDateFormat.format(date);
//        设置在comment里面的time
        comment.setTime(newDate);
        return commentMapper.insertComment(comment);
    }
}
