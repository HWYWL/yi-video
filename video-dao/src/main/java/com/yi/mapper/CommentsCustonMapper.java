package com.yi.mapper;

import com.yi.model.Comments;
import com.yi.utils.MyMapper;
import com.yi.vo.CommentsVO;

import java.util.List;

public interface CommentsCustonMapper  extends MyMapper<Comments> {
    /**
     * 查询视频留言
     * @param videoId 视频id
     * @return
     */
    List<CommentsVO> queryComments(String videoId);
}