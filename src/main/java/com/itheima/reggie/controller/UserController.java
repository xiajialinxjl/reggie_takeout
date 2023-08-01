package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/31 16:20
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user){

        //判断是否为新用户，如果是则完成注册
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,user.getPhone());
        //查询
        User user1 = userService.getOne(queryWrapper);
        if(user1==null){
            User user2 = new User();
            user2.setPhone(user.getPhone());
            user2.setStatus(1);
            userService.save(user2);
            //登录成功
            User tmp = userService.getOne(queryWrapper);
            request.getSession().setAttribute("user",tmp.getId());
            return R.success(user2);
        }
        //登录成功
        request.getSession().setAttribute("user",user1.getId());
        return R.success(user1);
    }
}
