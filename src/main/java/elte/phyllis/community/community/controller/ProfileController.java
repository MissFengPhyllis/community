package elte.phyllis.community.community.controller;

import elte.phyllis.community.community.dto.PaginationDTO;
import elte.phyllis.community.community.mapper.UserMapper;
import elte.phyllis.community.community.model.User;
import elte.phyllis.community.community.services.QuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionServices questionServices;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          Model model,
                          HttpServletRequest request){
//这个是在用户第一次访问这个页面的时候，需要去校验cookie
        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null &&  cookies.length!=0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if(user ==null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            //如果当前参数是question的话，就在页面上添加一个section名为questions
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(action)) {
            //如果当前参数是question的话，就在页面上添加一个section名为questions
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我的回复");
        }
        PaginationDTO pagination = questionServices.listByUserId(user.getId(),page,size);
        model.addAttribute("pagination",pagination);
        return "profile";

    }
}
