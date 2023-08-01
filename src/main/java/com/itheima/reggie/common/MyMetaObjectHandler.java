package com.itheima.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/30 8:25
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
//    @Autowired
//    private HttpServletRequest request;
    @Override
    public void insertFill(MetaObject metaObject) {
        //获得当前登录用户id
//        Long empId = (Long) request.getSession().getAttribute("employee");
        Long empId = BaseContext.getId();
        log.info("得到请求对象{}",empId);
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", empId);
        metaObject.setValue("updateUser", empId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //获得当前登录用户id
//        Long empId = (Long) request.getSession().getAttribute("employee");
        Long empId = BaseContext.getId();
        log.info("得到请求对象{}",empId);
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", empId);
    }
}
