package com.itheima.reggie.common;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/30 8:57
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setId(Long id){
        threadLocal.set(id);
    }

    public static long getId() {
        return threadLocal.get();
    }
}
