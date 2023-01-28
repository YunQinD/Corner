package com.example.corner.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.corner.mapper.ImgMapper;
import com.example.corner.pojo.Img;
import com.example.corner.service.ImgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

@Service
public class ImgServiceImpl extends ServiceImpl<ImgMapper, Img> implements ImgService {

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean upload(byte[] bytes) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public byte[] getByteFromFile (File file){
        byte[] be = null;
        try{
            if(file == null){
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4000);
            byte[] b = new byte[4000];
            int n;
            while((n = in.read(b)) != -1){
                out.write(b,0,n);
            }
            in.close();
            out.close();
            be = out.toByteArray();
        }catch(Exception e){
            e.printStackTrace();
        }
        return be;
    }
}
