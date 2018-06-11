package com.yi.mapper;

import com.yi.model.UsersFans;
import com.yi.model.UsersFansExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersFansMapper {
    long countByExample(UsersFansExample example);

    int deleteByExample(UsersFansExample example);

    int deleteByPrimaryKey(String id);

    int insert(UsersFans record);

    int insertSelective(UsersFans record);

    List<UsersFans> selectByExample(UsersFansExample example);

    UsersFans selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UsersFans record, @Param("example") UsersFansExample example);

    int updateByExample(@Param("record") UsersFans record, @Param("example") UsersFansExample example);

    int updateByPrimaryKeySelective(UsersFans record);

    int updateByPrimaryKey(UsersFans record);
}