package elte.phyllis.community.community.controller;

import elte.phyllis.community.community.dto.PaginationDTO;
import elte.phyllis.community.community.mapper.UserMapper;
import elte.phyllis.community.community.services.QuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

//controller的意思就是说，允许这个类去接受前端页面的请求
@Controller
public class IndexController {

    //这里需要把这个usermapper注入进来，这样才能访问得到我们的数据库表里面的user表
    @Autowired
    private UserMapper userMapper;
    //这个是一个路由，后面想要访问这个函数的话，就直接访问根目录就行
    @Autowired
    private QuestionServices questionServices;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
                        ){
        //获取question里面的所有的信息
        PaginationDTO pagination = questionServices.list(page,size);
        model.addAttribute("pagination",pagination);
        //这里的token怎么来那，当然还是通过httpserletrequest来获取
        //我们需要去定义一个通过找到token来确定用户的一个函数，当然controller里面只管自己的业务逻辑，不管实际的这个差找的

        return "index";
    }
}
