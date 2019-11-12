package com.gw.forum.forum.advice;

import com.alibaba.fastjson.JSON;
import com.gw.forum.forum.controller.CustomizeErrorController;
import com.gw.forum.forum.dto.ResultDTO;
import com.gw.forum.forum.exception.CustomizaErrorCode;
import com.gw.forum.forum.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//服务器异常
//已知异常反馈
@ControllerAdvice
public class CustomizeExceptionHander {
//    接受异常（异常范围）
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                        HttpServletResponse response,
                        Throwable ex,
                        Model model) {
        String contentType = request.getContentType();
        if (("application/json").equals(contentType)){
            ResultDTO resultDTO=null;
            if (ex instanceof CustomizeException){
                resultDTO=ResultDTO.errorOf((CustomizeException)ex);
            }else{
                new CustomizeErrorController().handleError(request, model);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message","服务太热啦!!!等一下再来试试?");
            }
            return new ModelAndView("error");
        }
    }
}
