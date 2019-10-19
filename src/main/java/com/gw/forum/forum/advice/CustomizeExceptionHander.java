package com.gw.forum.forum.advice;

import com.gw.forum.forum.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//编写已知异常提示
@ControllerAdvice
public class CustomizeExceptionHander {
    //    接受异常（异常范围）
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable ex, Model model) {
        if (ex instanceof CustomizeException){
            model.addAttribute("message", ex.getMessage());
        }
        return new ModelAndView("error");
    }
}
