package com.gw.forum.forum.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
//编写未知异常提示
@Controller
public class CustomizeErrorController implements ErrorController {
    @RequestMapping("error")
    public String handleError(HttpServletRequest request,
                              Model model){
        HttpStatus status = this.getStatus(request);
        model.addAttribute("message", status);
        if (status.is4xxClientError()){
            model.addAttribute("message","你这个请求错了吧，要不要换一个姿势");
        }else if (status.is5xxServerError()){
            model.addAttribute("message","服务太热啦，要不然等一下再来试试");
        }
        return "error"; // 返回其它错误的页面
    }
    @Override
    public String getErrorPath() {
        return "error";
    }
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
