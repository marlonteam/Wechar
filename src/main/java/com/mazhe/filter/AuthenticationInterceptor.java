package com.mazhe.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.mazhe.dao.UserRepository;
import com.mazhe.domain.BaseMessage;
import com.mazhe.domain.User;
import com.mazhe.service.ManageService;
import com.mazhe.token.CheckToken;
import com.mazhe.token.JwtUtil;
import com.mazhe.token.LoginToken;
import com.mazhe.util.JsonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserRepository userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        BaseMessage baseMessage=new BaseMessage();
        baseMessage.setCode("500");
        httpServletResponse.setContentType("application/json");
        ServletOutputStream outputStream= httpServletResponse.getOutputStream();
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有LoginToken注释，有则跳过认证
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()) {
                return true;
            }
        }

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(CheckToken.class)) {
            CheckToken checkToken = method.getAnnotation(CheckToken.class);
            if (checkToken.required()) {
                // 执行认证
                if (token == null) {
                    baseMessage.setMessage("无token，请重新登录");
                    outputStream.write(JsonUtilities.toJSon(baseMessage).getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();
                    return false;
//                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getClaim("id").asString();
                } catch (JWTDecodeException j) {
                    baseMessage.setMessage("访问异常");
                    outputStream.write(JsonUtilities.toJSon(baseMessage).getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();
                    return false;
//                    throw new RuntimeException("访问异常！");
                }
                Optional<User> user = userService.findOneByOpenId(userId);
                if (!user.isPresent()) {
                    baseMessage.setMessage("用户不存在，请重新登录");
                    outputStream.write(JsonUtilities.toJSon(baseMessage).getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();
                    return false;
//                    throw new RuntimeException("用户不存在，请重新登录");
                }
                Boolean verify = JwtUtil.isVerify(token);
                if (!verify) {
                    baseMessage.setMessage("非法访问");
                    outputStream.write("非法访问".getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();
                    return false;
//                    throw new RuntimeException("非法访问！");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}
