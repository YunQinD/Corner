package com.example.corner.uitls;

import org.springframework.web.multipart.MultipartFile;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

/**
 * @Time : 2023/1/25-17:06
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *文件操作工具类*
 */
public class FileUtils {

    private static final String staticUrl = "http://39.101.75.120:8080/pictures/";
    private static final String filePath = "/pictures";
    private static final String LOCAL_URL = "http://127.0.0.1:8802/feo/";

    /**上传文件的函数*/
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + "/" + fileName);
        out.write(file);
        out.flush();
        out.close();
    }


    /**保存图片的函数*/
    public static APIResponse savePictures(MultipartFile file) {

        Date date = new Date(System.currentTimeMillis());
        String dateTime = date.toString();

        String fileName = dateTime + file.getOriginalFilename();

        if (file.isEmpty()) {
            return APIResponse.error(ErrorCode.IMAGE_UPLOADING_ERROR, "文件为空");
        }

        try {
            uploadFile(file.getBytes(), filePath, fileName);
            String url = staticUrl + fileName;
            return APIResponse.success(url);
        } catch (Exception e) {
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR, "图片上传服务失败;[ImgController]");
        }
    }

    /**
     * 方式二压缩 Google大法 因为Thumbnails.of() 方法是一个重载方法，参数不仅仅局限于是一个文件类型 可以是以流的形式 File形式，ImageBuffer对象，URL路径,String类型指定文件路径
     * 然后可通过链式构造器传入不通参数，压缩比例，指定输出的格式等最终通过toFile("文件存储路径")返回一个已经压缩完成的图片。
     * @param file 待压缩的文件
     * @return 压缩后图片路径 这个可自己指定
     */
    public static APIResponse thumbnail(MultipartFile file) {
        //得到上传时的原文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件格式
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //获取uuid作为文件名
        String name = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            // 先尝试压缩并保存图片
            Thumbnails.of(file.getInputStream()).scale(0.5f)
                    .outputQuality(0.15f)
                    .outputFormat("jpeg")
                    .toFile("/image/" + name);
        } catch (IOException e) {
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR);
        }
        return APIResponse.success(staticUrl.concat(name).concat(".").concat("jpeg"));
    }

}
