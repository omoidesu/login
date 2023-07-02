package com.omoi.mapper;

import com.omoi.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author omoi
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-07-02 05:39:35
* @Entity com.omoi.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




