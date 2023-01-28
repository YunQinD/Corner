package com.example.corner.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.corner.uitls.APIResponse;
import com.example.corner.uitls.ErrorCode;
import com.example.corner.uitls.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @Time : 2023/1/6-22:20
 * @Author : YunQin2
 * @Email : yongqi_du21@tju.edu.cn
 * @Description : *拦截器类*
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null){
            APIResponse apiResponse = APIResponse.error(ErrorCode.NOT_LOGIN_YET);
            String content = JSON.toJSONString(apiResponse);
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(content);
            return false;
        }
        Claims claims = JwtUtil.verifyJwt(token);
        if (claims == null){
            APIResponse apiResponse = APIResponse.error(ErrorCode.TOKEN_LOGIN_ERROR);
            String content = JSON.toJSONString(apiResponse);
            response.setCharacterEncoding("utf-8");
            response.getWriter().println(content);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
