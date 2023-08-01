package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/31 16:16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
