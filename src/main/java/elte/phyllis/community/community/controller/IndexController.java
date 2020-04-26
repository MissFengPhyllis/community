package elte.phyllis.community.community.controller;

import elte.phyllis.community.community.mapper.UserMapper;
import elte.phyllis.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

//controller的意思就是说，允许这个类去接受前端页面的请求
@Controller
public class IndexController {

    //这里需要把这个usermapper注入进来，这样才能访问得到我们的数据库表里面的user表
    @Autowired
    private UserMapper userMapper;
    //这个是一个路由，后面想要访问这个函数的话，就直接访问根目录就行
    @GetMapping("/")
//    public String hello(@RequestParam(name = "name") String name, Model model){
//        //当然这里是需要将要在url上请求的参数放入到requestparam里面
//        //这个model的作用就是数据的传输，这里的name参数其实是在hello.html文件里面的一个形参
//        //所以如果这里不把参数传进去的话，到时候访问页面的时候就不会有参数了
//        model.addAttribute("name", name);
//        return "index";
//    }
    public String index(HttpServletRequest request){
        //我们只知道request.getcookies，但是不知道这个结果是啥，那就直接control+alt+V,自己就实例化了
        Cookie[] cookies = request.getCookies();//由于这里获取到的cookie是一个数组，那么先循环看一下这里里面都是啥
        //这里直接用cookiles.for这是一个快捷键，输入以后回车键，就出现了下面的增强For循环的格式，节省很多时间
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
        //这里的token怎么来那，当然还是通过httpserletrequest来获取
        //我们需要去定义一个通过找到token来确定用户的一个函数，当然controller里面只管自己的业务逻辑，不管实际的这个差找的

        return "index";
    }
}
