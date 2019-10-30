package com.gw.forum.forum.cache;

import com.gw.forum.forum.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOList=new ArrayList<>();
        TagDTO language=new TagDTO();
        language.setCategoryName("开发语言");
        language.setTags(Arrays.asList("javascript","php","css","html","html5","java","node.js","python","c++","c","golang","objective-c","typescript","shell","c#","swift","sass","bash","ruby","less","asp.net","lua","scala","coffeescript","actionscript","rust","erlang","perl"));
        tagDTOList.add(language);

        TagDTO frame=new TagDTO();
        frame.setCategoryName("平台框架");
        frame.setTags(Arrays.asList("laravel","spring","express","django","flask","yii","ruby-on-rails","tornado","koa","struts"));
        tagDTOList.add(frame);

        TagDTO server=new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","缓存","tomcat","负载均衡","unix","hadoop","windows-server"));
        tagDTOList.add(server);

        TagDTO database=new TagDTO();
        database.setCategoryName("数据库和缓存");
        database.setTags(Arrays.asList("mysql","redis","mongodb","sql","oracle","nosql","memcached","sqlserver","postgresql","sqlite"));
        tagDTOList.add(database);

        TagDTO tool=new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","visual-studio-code","vim","sublime-text","xcode","intellij-idea","eclipse","maven","ide","svn","visual-studio","atom","emacs","textmate","hg"));
        tagDTOList.add(tool);

        TagDTO system=new TagDTO();
        system.setCategoryName("系统设备");
        system.setTags(Arrays.asList("android","ios","chrome","windows","iphone ","firefox","internet-explorer","safari","ipad","opera","apple-watch"));
        tagDTOList.add(system);

        TagDTO other=new TagDTO();
        other.setCategoryName("其他");
        other.setTags(Arrays.asList("html5","react.js","搜索引擎","virtualenv","lucene","thymeleaf"));
        tagDTOList.add(other);
        return tagDTOList;
    }
    public static String filterInvalid(String tags){
        String[] split= StringUtils.split(tags,",");
        List<TagDTO> tagDTOList=get();
        List<String> alltag = tagDTOList.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !alltag.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}