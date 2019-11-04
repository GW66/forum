package com.gw.forum.forum.controller;
/**
 *用于回复通知
 */
import com.gw.forum.forum.dto.NotificationDTO;
import com.gw.forum.forum.enums.NotificationEnum;
import com.gw.forum.forum.model.User;
import com.gw.forum.forum.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String revert(@PathVariable("id") Long id,
                         HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO=notificationService.read(id,user);
        if (notificationDTO.getType()== NotificationEnum.REPLY_COMMENT.getStatus()
        ||notificationDTO.getType()== NotificationEnum.REPLY_QUESTION.getStatus()){
            return "redirect:/revert/"+notificationDTO.getOuterid();
        }
        return "revert";
    }

}
