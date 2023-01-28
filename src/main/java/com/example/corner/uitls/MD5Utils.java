package com.example.corner.uitls;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Time : 2023/1/20-14:00
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *MD5工具类*
 */
public class MD5Utils {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "#%^*h&w_uf75";

    public static String inputPassToFormPass(String inputPass) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }


}
