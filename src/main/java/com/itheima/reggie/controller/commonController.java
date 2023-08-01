package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @Title:
 * @Author: xiajialin
 * @Date: 2023/7/30 15:30
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class commonController {

    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        String fileName = file.getOriginalFilename();
        //使用UUID重新生成文件名，防止文件名重复造成文件覆盖
        String suffix = fileName.split("\\.")[1];
        String storageFileName = UUID.randomUUID().toString()+"."+suffix;

        //创建一个目录对象
        File dir = new File(basePath);
        if(!dir.exists()){
             dir.mkdir();
        }
        try {
            file.transferTo(new File(basePath+storageFileName));
        }catch (IOException e){
            e.printStackTrace();
        }
        return R.success(storageFileName);
    }
    @GetMapping("/download")
    public void dowload(String name , HttpServletResponse response){
        //输入流读取文件
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+name));
            //输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while((len=fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            //关闭
            outputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
