package com.example.shuchengcommunity.controller.provider;

import com.alibaba.fastjson.JSON;
import com.example.shuchengcommunity.dto.AssessTokenDTO;
import com.example.shuchengcommunity.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description
 * @Author shucheng
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2022/10/9
 */
@Component
public class GithubProvider {
    public String getAccessToken( AssessTokenDTO assessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create( JSON.toJSONString(assessTokenDTO),mediaType);
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try(Response response = client.newCall(request).execute()){
                String string= response.body().string();
                String token = string.split("&")[0].split("=")[1];
                return token;
        } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

    }
     public GithubUser getUser(String accessToken){
         OkHttpClient client = new OkHttpClient();
         Request request = new Request.Builder()
                 .url("https://api.github.com/user")
                 .header("Authorization","token "+accessToken)
                 .build();

         try{ Response response = client.newCall(request).execute();
              String string = response.body().string();
              GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
              return githubUser ;
         } catch (IOException e) {

         }
         return null;

     }

}
