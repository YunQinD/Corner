package com.example.corner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.corner.pojo.Img;
import java.io.File;

/**
 * @Time : 2023/1/18-20:57
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : **
 */
public interface ImgService extends IService<Img> {
    Boolean upload(byte[] bytes);
    byte[] getByteFromFile (File file);
}
