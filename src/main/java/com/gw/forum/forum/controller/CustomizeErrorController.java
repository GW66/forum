package com.gw.forum.forum.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
//浏览器反馈异常
//未知异常反馈
@Controller
@RequestMapping("error")
public class CustomizeErrorController implements ErrorController {
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView handleError(HttpServletRequest request,
                                    Model model){
        HttpStatus status = getStatus(request);
        if (status.is4xxClientError()){
            model.addAttribute("message",status+"你这个请求错了吧，要不要换一个姿势?");
        }else if (status.is5xxServerError()){
            model.addAttribute("message",status+"服务太热啦，要不然等一下再来试试?");
        }
        return new ModelAndView("error"); // 返回其它错误的页面
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
