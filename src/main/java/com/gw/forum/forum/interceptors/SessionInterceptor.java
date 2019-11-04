package com.gw.forum.forum.interceptors;

import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.model.UserExample;
import com.gw.forum.forum.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //        获取Cookie
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(cookie.getValue());
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size()!=0){
                        //            创建session
                        HttpSession session=request.getSession();
                        session.setAttribute("user",users.get(0));
                        //        统计未读数据
                        Long unreadCount=notificationService.unreadCount(users.get(0).getId());
                        session.setAttribute("unreadCount",unreadCount);

                    }else {
                        Cookie token=new Cookie("token",null);
                        token.setMaxAge(0);
                        response.addCookie(token);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
