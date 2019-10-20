package com.gw.forum.forum.advice;

import com.gw.forum.forum.controller.CustomizeErrorController;
import com.gw.forum.forum.controller.PublishController;
import com.gw.forum.forum.controller.RevertController;
import com.gw.forum.forum.exception.CustomizeException;
import com.gw.forum.forum.service.QuestionService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//服务器异常
//已知异常反馈
@ControllerAdvice(assignableTypes = {RevertController.class,PublishController.class})
public class CustomizeExceptionHander {
//    接受异常（异常范围）
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                        Throwable ex,
                        Model model) {
//        用于判断是否是已知异常
        if (ex instanceof CustomizeException){
            model.addAttribute("message", ex.getMessage());
        }else{
            new CustomizeErrorController().handleError(request,model);
        }
        return new ModelAndView("error");
    }
}
