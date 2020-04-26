package elte.phyllis.community.community.controller;

import com.sun.javaws.jnl.IconDesc;
import elte.phyllis.community.community.dto.AccessTokenDTO;
import elte.phyllis.community.community.dto.GithubUser;
import elte.phyllis.community.community.mapper.UserMapper;
import elte.phyllis.community.community.model.User;
import elte.phyllis.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.redirect_uri}")
    private String clientRedirect_uri;

    @Autowired(required=false)
    private UserMapper userMapper;

    //callback重定向后返回到index页面
    @GetMapping("/callback")//这里面传递的参数是要从url里面获取的，第一个是code, 还有一个是state
    public String callback(@RequestParam (name="code") String code,
                           @RequestParam (name="state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientRedirect_uri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
//        System.out.println(user.getName());
        if(githubUser != null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());//用UUID的随机id
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //登录成功，写session,session 是在HttpServletRequest request里面拿到的,把user对象放到session里面
            request.getSession().setAttribute("user",githubUser);
            //登录成功后重定向到默认的index页面，这里的参数只能是mapping的定义的路由，不能直接写index
            return "redirect:/";
        }else{
        //登录失败
            return "redirect:/";
        }
    }
}