package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/29 8:58
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
