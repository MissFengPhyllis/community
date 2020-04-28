package elte.phyllis.community.community.services;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import elte.phyllis.community.community.dto.QuestionDTO;
import elte.phyllis.community.community.mapper.QuestionMapper;
import elte.phyllis.community.community.mapper.UserMapper;
import elte.phyllis.community.community.model.Question;
import elte.phyllis.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServices {

    @Autowired
    private UserMapper userMapper;
    //这个是一个路由，后面想要访问这个函数的话，就直接访问根目录就行
    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> list() {
        List<Question> questions= questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getId());
            //把question里面的对象都放入到DTO里面去
            QuestionDTO questionDTO = new QuestionDTO();

            //这里可以用传统的方法，一个一个的去设置questionDTO.setId(question.getId());
            //这里使用spring的一个复制方法
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
    ///目的在于这里面不仅可以使用questionmapper，还能用usermapper，起到一个组装的作用
}
