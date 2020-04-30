package elte.phyllis.community.community.services;

import elte.phyllis.community.community.dto.PaginationDTO;
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
///目的在于这里面不仅可以使用questionmapper，还能用usermapper，起到一个组装的作用
@Service
public class QuestionServices {

    @Autowired
    private UserMapper userMapper;
    //这个是一个路由，后面想要访问这个函数的话，就直接访问根目录就行
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        if(page<1){
            page = 1;
        }else if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        //size*(page-1)
        Integer offset  =size * (page-1);
        List<Question> questions= questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            //这里要找到user的头像，肯定是传递的是question里面的creator的值啊，肯定不是getID，那个question的ID，跟USERID 没关系
            User user = userMapper.findById(question.getCreator());
            //把question里面的对象都放入到DTO里面去
            QuestionDTO questionDTO = new QuestionDTO();

            //这里可以用传统的方法，一个一个的去设置questionDTO.setId(question.getId());
            //这里使用spring的一个复制方法
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO listByUserId(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        paginationDTO.setPagination(totalCount,page,size);
        if(page<1){
            page = 1;
        }else if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        //size*(page-1)
        Integer offset  =size * (page-1);
        List<Question> questions= questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            //这里要找到user的头像，肯定是传递的是question里面的creator的值啊，肯定不是getID，那个question的ID，跟USERID 没关系
            User user = userMapper.findById(question.getCreator());
            //把question里面的对象都放入到DTO里面去
            QuestionDTO questionDTO = new QuestionDTO();

            //这里可以用传统的方法，一个一个的去设置questionDTO.setId(question.getId());
            //这里使用spring的一个复制方法
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        System.out.println(question);
        if(question.getId() == null){

            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }

    public void deleteById(Integer id) {
        questionMapper.delete(id);
    }
}
