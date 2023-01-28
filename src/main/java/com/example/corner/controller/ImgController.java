package com.example.corner.controller;

import com.example.corner.mapper.UserMapper;
import com.example.corner.uitls.APIResponse;
import com.example.corner.uitls.ErrorCode;
import com.example.corner.uitls.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.Calendar;


/**
 * @Time : 2023/1/18-20:55
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *图片上传*
 */
@Log4j2
@RestController
@RequestMapping("/saveProfiles")
public class ImgController {

    private final UserMapper userMapper;

    @Autowired
    public ImgController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostMapping //返回json数据
    public APIResponse uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("uid") String uid, HttpServletRequest request) {

        try {
            APIResponse response = FileUtils.savePictures(file);
            String url = response.getMessage();
            if (userMapper.updateAvatarByUid(url, uid)) {
                return APIResponse.success("头像上传成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return APIResponse.error(ErrorCode.SERVICE_ERROR, "头像上传服务失败;[ImgController]");
        }
        return APIResponse.error(ErrorCode.IMAGE_UPLOADING_ERROR, "头像上传失败;[ImgController]");
    }


}

    /*public static byte[] compressImage(byte[] imageByte, int ppi) throws Exception {
        byte[] smallImage = null;
        int width = 0, height = 0;

        if (imageByte == null){
            return null;}

        ByteArrayInputStream byteInput = new ByteArrayInputStream(imageByte);
        try {
            Image image = ImageIO.read(byteInput);
            int w = image.getWidth(null);
            int h = image.getHeight(null);
            // adjust weight and height to avoid image distortion
            double scale = 0;
            scale = Math.min((float) ppi / w, (float) ppi / h);
            width = (int) (w * scale);
            width -= width % 4;
            height = (int) (h * scale);

            if (scale >= (double) 1){
                return imageByte;}

            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            buffImg.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(buffImg, "png", out);
            smallImage = out.toByteArray();
            return smallImage;

        }catch (IOException e) {
            log.error(e.getMessage());
            throw new Exception("");
        }*//*

    }*/







    /*@ResponseBody
    public class ImgController {
        @Autowired
        private ImgService imgService;

        public R upLoadImg(@RequestParam("images")File file, HttpServletRequest request){
            if(imgService.getByteFromFile(file)==null){
                return new R(false,null);
            }
            return new R(true,imgService.getByteFromFile(file));
        }

    }*/
