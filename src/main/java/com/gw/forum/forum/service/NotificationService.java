package com.gw.forum.forum.service;

import com.gw.forum.forum.dto.NotificationDTO;
import com.gw.forum.forum.dto.PaginationDTO;
import com.gw.forum.forum.enums.NotificationEnum;
import com.gw.forum.forum.enums.NotificationStatusEnum;
import com.gw.forum.forum.exception.CustomizaErrorCode;
import com.gw.forum.forum.exception.CustomizeException;
import com.gw.forum.forum.mapper.NotificationMapper;
import com.gw.forum.forum.mapper.UserMapper;
import com.gw.forum.forum.model.Notification;
import com.gw.forum.forum.model.NotificationExample;
import com.gw.forum.forum.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;
    public List<NotificationDTO> listpage(Long creator, Integer page, Integer size) {
        Integer startSize=(page-1)*size;
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(creator);
        notificationExample.setOrderByClause("gmt_create desc");
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample,new RowBounds(startSize, size));
        List<NotificationDTO> notificationDTOList=new ArrayList<>();
        for(Notification notification:notificationList) {
            NotificationDTO notificationDTO=new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }
        return notificationDTOList;
    }

    public PaginationDTO showpage(Long creator, Integer page, Integer size) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(creator);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample);
        PaginationDTO paginationDTO=new PaginationDTO(page,totalCount,size);
        return paginationDTO;
    }

    public Long unreadCount(Long creator) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(creator)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        long count = notificationMapper.countByExample(notificationExample);
        return count;
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification=notificationMapper.selectByPrimaryKey(id);
        if (notification.getReceiver()!=user.getId()){
            throw new CustomizeException(CustomizaErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO=new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
