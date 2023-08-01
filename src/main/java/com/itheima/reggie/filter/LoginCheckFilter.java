package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/29 10:03
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("拦截到请求:{}",request.getRequestURI());

        //获取请求URI
        String requestURI = request.getRequestURI();
        String[] urls = new String[]{
//                "/**",
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };
        //判断请求是否需要处理
        boolean check = check(urls,requestURI);
        //如果不需要处理，直接放行
        if(check){
            log.info("不需要处理请求:{}",requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        //1.判断后台登录状态，如登录直接放行
        if((request.getSession().getAttribute("employee"))!=null){
            //放行
            Long empId = (Long) request.getSession().getAttribute("employee");
            log.info("用户已经登陆，id={}",empId);
            BaseContext.setId(empId);

            filterChain.doFilter(request,response);
            return;
        }
        //2.判断前台登录状态，如登录直接放行
        if((request.getSession().getAttribute("user"))!=null){
            //放行
            Long userId = (Long) request.getSession().getAttribute("user");
            log.info("前台用户已经登陆，id={}",userId);
            BaseContext.setId(userId);

            filterChain.doFilter(request,response);
            return;
        }
        //如果未登录，返回未登录结果，通过输出流向客户端页面响应数据
        log.info("未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url,requestURI);
            if(match) return true;
        }
        return false;
    }
}
