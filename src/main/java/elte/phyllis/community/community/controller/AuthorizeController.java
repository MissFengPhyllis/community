package elte.phyllis.community.community.controller;

import com.sun.javaws.jnl.IconDesc;
import elte.phyllis.community.community.dto.AccessTokenDTO;
import elte.phyllis.community.community.dto.GithubUser;
import elte.phyllis.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    //caback重定向后返回到index页面
    @GetMapping("/callback")//这里面传递的参数是要从url里面获取的，第一个是code, 还有一个是state
    public String callback(@RequestParam (name="code") String code,
                           @RequestParam (name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientRedirect_uri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());

        return "index";
    }
}
