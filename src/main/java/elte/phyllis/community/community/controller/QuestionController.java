package elte.phyllis.community.community.controller;

import elte.phyllis.community.community.dto.QuestionDTO;
import elte.phyllis.community.community.mapper.QuestionMapper;
import elte.phyllis.community.community.services.QuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionServices questionServices;
    @GetMapping("/question/{id}")
    public String question( @PathVariable(name = "id") Integer id,
                            Model model){
        QuestionDTO questionDTO = questionServices.getById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
