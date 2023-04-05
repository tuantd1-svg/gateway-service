package com.example.gatewayservice.consumer;

import com.example.commonapi.model.ResultMessage;
import com.example.commonapi.model.User;
import com.example.commonapi.parameter.enumable.EMessage;
import com.example.commonapi.parameter.enumable.ETransactionStatus;
import com.example.commonapi.parameter.enumable.ErrorCode;
import com.example.gatewayservice.mapper.UserMapper;
import com.example.gatewayservice.service.UserService;
import com.example.queuecommonapi.config.QueueConfig;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class QueueConsumer {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @RabbitHandler
    @RabbitListener(queues = QueueConfig.Q_CORE_CREATE_USER)
    public ResultMessage addNewUser(@Payload User user) {
        try {
            return userService.addNewUser(userMapper.map(user)).block();
        } catch (Exception e) {
            return new ResultMessage(ErrorCode.FAILURE.getCode(), ErrorCode.FAILURE.getMessage() + " - " + e.getMessage(), false, EMessage.INTERNAL_ERROR);
        }
    }
}
