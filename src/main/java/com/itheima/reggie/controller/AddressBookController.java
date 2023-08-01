package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/31 17:13
 */
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId((BaseContext.getId()));
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }
    @PutMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        LambdaUpdateWrapper<AddressBook>  queryWrapper = new LambdaUpdateWrapper<>();
        long tmp = BaseContext.getId();
        //update address_book set is_default=0 where user_id = ?
        queryWrapper.eq(AddressBook::getUserId,BaseContext.getId());
        //1表示默认地址
        queryWrapper.set(AddressBook::getIsDefault,0);
        //将用户ID下所有地址全部改成非默认
        addressBookService.update(queryWrapper);

        addressBook.setIsDefault(1);
        //update address_book set is_default=1 where user_id = ?
        //将该地址ID所在地址改成默认
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }
    /**
     * 查询默认地址
     */
    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        //select * from address_book where user_id = ? and is_default = 1
        queryWrapper.eq(AddressBook::getUserId,BaseContext.getId());
        queryWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if(null == addressBook){
            return R.error("没有默认地址");
        }else {
            return R.success(addressBook);
        }
    }
    /**
     * 查询所有地址
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getId());

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null!=addressBook.getUserId(),AddressBook::getUserId,addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);

        return R.success(addressBookService.list(queryWrapper));
    }
}
