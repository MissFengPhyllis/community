package elte.phyllis.community.community.interceptor;

import elte.phyllis.community.community.mapper.UserMapper;
import elte.phyllis.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //我们只知道request.getcookies，但是不知道这个结果是啥，那就直接control+alt+V,自己就实例化了
        Cookie[] cookies = request.getCookies();//由于这里获取到的cookie是一个数组，那么先循环看一下这里里面都是啥
        //这里直接用cookiles.for这是一个快捷键，输入以后回车键，就出现了下面的增强For循环的格式，节省很多时间
        if(cookies != null &&  cookies.length!=0){
            for (Cookie cookie : cookies) {
                //如果这歌cookie的list有一个名字是token的话，那么就将这个token存下来
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    //这里如果检查到有这个user的话，就把user的信息放到session里面，这样前端页面就能获取到用户的值了
                    if(user != null){
                        request.getSession().setAttribute("user",user);
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
