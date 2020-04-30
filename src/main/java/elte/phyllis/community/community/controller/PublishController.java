package elte.phyllis.community.community.controller;

import elte.phyllis.community.community.dto.QuestionDTO;
import elte.phyllis.community.community.mapper.QuestionMapper;
import elte.phyllis.community.community.mapper.UserMapper;
import elte.phyllis.community.community.model.Question;
import elte.phyllis.community.community.model.User;
import elte.phyllis.community.community.services.QuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionServices questionServices;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id") Integer id,
                       Model model){
        QuestionDTO question = questionServices.getById(id);
        //将获取到的元素填充到页面上
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";

    }
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
            @RequestParam(value = "id",required = false) Integer id,
            HttpServletRequest request,
            Model model){
        //将我们输入到对话框的中的数据报错起来
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("id",id);
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

        User user = (User)request.getSession().getAttribute("user");

        if(user == null){
            model.addAttribute("error","sorry, please sign in");
            return "redirect:/";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtModified(System.currentTimeMillis());
        question.setId(id);
        questionServices.createOrUpdate(question);
        return "redirect:/";
    }
}
