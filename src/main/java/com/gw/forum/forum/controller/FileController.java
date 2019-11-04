package com.gw.forum.forum.controller;

import com.gw.forum.forum.dto.FileDTO;
import com.gw.forum.forum.provider.ALiYunProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class FileController {
    @Autowired
    private ALiYunProvider aLiYunProvider;
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest)request;
        MultipartFile file=multipartRequest.getFile("editormd-image-file");
        String filename=file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        String fileUrl=aLiYunProvider.upload(filename,inputStream);
        FileDTO fileDTO=new FileDTO();
        if (StringUtils.isNotBlank(fileUrl)){
            fileDTO.setSuccess(1);
            fileDTO.setMessage("上传成功");
            fileDTO.setUrl(fileUrl);
        }else {
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败");
        }
        return fileDTO;
    }
}
