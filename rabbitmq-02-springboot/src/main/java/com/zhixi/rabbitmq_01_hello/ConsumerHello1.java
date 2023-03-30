package com.zhixi.rabbitmq_01_hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName ConsumerHello1
 * @Author zhangzhixi
 * @Description 直连模型
 * @Date 2023-03-28 21:57
 * @Version 1.0
 */
/**
 * @RabbitListener：设置监听的队列名称
 * @Queue：设置队列名称，默认持久化、非独占、不自动删除队列
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(value = "hello"))
public class ConsumerHello1 {

    @RabbitHandler
    public void receive1(String message) {
        System.out.println("消费者消费到的消息是：" + message);
    }
}
