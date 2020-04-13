package elte.phyllis.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//controller的意思就是说，允许这个类去接受前端页面的请求
@Controller
public class IndexController {
    //这个是一个路由，后面想要访问这个函数的话，就直接访问根目录就行
    @GetMapping("/")
//    public String hello(@RequestParam(name = "name") String name, Model model){
//        //当然这里是需要将要在url上请求的参数放入到requestparam里面
//        //这个model的作用就是数据的传输，这里的name参数其实是在hello.html文件里面的一个形参
//        //所以如果这里不把参数传进去的话，到时候访问页面的时候就不会有参数了
//        model.addAttribute("name", name);
//        return "index";
//    }
    public String index(){
        return "index";
    }
}
