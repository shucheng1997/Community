package com.example.shuchengcommunity.controller;

import com.example.shuchengcommunity.controller.provider.GithubProvider;
import com.example.shuchengcommunity.dto.AssessTokenDTO;
import com.example.shuchengcommunity.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author shucheng
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/10/9
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
     private String clientId;
    @Value("${github.client.secret}")
     private String clientSecret;
    @Value("${github.redirect.uri}")
     private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,@RequestParam(name= "state") String state){
        AssessTokenDTO assessTokenDTO = new AssessTokenDTO();
        assessTokenDTO.setCode(code);
        assessTokenDTO.setClient_id(clientId);
        assessTokenDTO.setRedirect_uri(redirectUri);
        assessTokenDTO.setClient_secret(clientSecret);
        assessTokenDTO.setState(state);
        String accssToken=githubProvider.getAccessToken(assessTokenDTO);
        GithubUser githubUser=githubProvider.getUser(accssToken);
        System.out.println(githubUser.getName());
      return "index";
    }

}
