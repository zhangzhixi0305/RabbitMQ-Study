package com.zhixi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @ClassName RabbitMQTest
 * @Author zhangzhixi
 * @Description
 * @Date 2023-03-28 21:33
 * @Version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 直连
     */
    @Test
    public void testHelloWorld() {
        // 生产者向队列发送消息，需要注意的是，此处不会创建队列，是由消费者端创建的交换机/队列
        rabbitTemplate.convertAndSend("hello", "send Message To MQ");
    }

    /**
     * WorkQueue
     */
    @Test
    public void testWork() {
        // 生产者向队列发送消息，需要注意的是，此处不会创建队列，是由消费者端创建的交换机/队列
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", "send Message To Work Queue：" + i);
        }
    }

    /**
     * 扇出/广播
     */
    @Test
    public void testFanout() {
        // 生产者向队列发送消息，需要注意的是，此处不会创建队列，是由消费者端创建的交换机/队列
        rabbitTemplate.convertAndSend("logs", "", "Fanout发送的消息");
    }


    /**
     * 路由模式-Direct
     */
    @Test
    public void testRouteDirect() {
        // 生产者向队列发送消息，需要注意的是，此处不会创建队列，是由消费者端创建的交换机/队列
        rabbitTemplate.convertAndSend("logs_direct", "info", "Route-Direct模型发送的【info】类型消息");
        rabbitTemplate.convertAndSend("logs_direct", "warning", "Route-Direct模型发送的【warning】类型消息");
        rabbitTemplate.convertAndSend("logs_direct", "error", "Route-Direct模型发送的【error】类型消息");
    }

    /**
     * 路由模式-Topic
     */
    @Test
    public void testRouteTopic() {
        // 生产者向队列发送消息，需要注意的是，此处不会创建队列，是由消费者端创建的交换机/队列
        rabbitTemplate.convertAndSend("topics", "user.save", "user.save路由消息");
        rabbitTemplate.convertAndSend("topics", "user.insert.message", "user.insert.message的路由消息");
        rabbitTemplate.convertAndSend("topics", "man.user.insert", "man.user.insert路由消息");
    }

}
