package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/30 9:24
 */
public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
