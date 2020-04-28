package elte.phyllis.community.community.controller;

import elte.phyllis.community.community.mapper.QuestionMapper;
import elte.phyllis.community.community.mapper.UserMapper;
import elte.phyllis.community.community.model.Question;
import elte.phyllis.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return"publish";
    }

    //这里为让页面保留我们输入的值，但是这些值暂时可以不是必须的
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            HttpServletRequest request,
            Model model){
        //将我们输入到对话框的中的数据报错起来
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title == null || title == ""){
            model.addAttribute("error","the title cannot be null");
            return "publish";
        } else if(description == null || description == ""){
            model.addAttribute("error","the description cannot be null");
            return "publish";
        } else if(tag == null || tag == ""){
            model.addAttribute("error","the tag cannot be null");
            return "publish";
        }

        User user = null;

        Cookie[] cookies = request.getCookies();
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
        if(user == null){
            model.addAttribute("error","sorry, please sign in");
            return "redirect:/";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionMapper.create(question);
        return "redirect:/";
    }
}
