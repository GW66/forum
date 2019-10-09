package com.gw.forum.forum.provider;

import com.alibaba.fastjson.JSON;
import com.gw.forum.forum.dto.AccessTokenDTO;
import com.gw.forum.forum.dto.GitlabUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class GitlabProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("http://192.168.31.174/oauth/token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
//            截取accessToken
            String[] split=string.split(",");
            String[] strings=split[0].split(":");
            StringBuilder access_Token=new StringBuilder(strings[1]);
            String accessToken=access_Token.substring(1,access_Token.length()-1);
            System.out.println(accessToken);
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GitlabUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.31.174/api/v4/user?access_token="+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            GitlabUser gitlabUser = JSON.parseObject(string, GitlabUser.class);
            return gitlabUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
