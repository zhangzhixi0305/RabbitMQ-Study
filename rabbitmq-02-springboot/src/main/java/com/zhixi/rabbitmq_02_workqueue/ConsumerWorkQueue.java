package com.zhixi.rabbitmq_02_workqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName ConsumerWorkQueue
 * @Author zhangzhixi
 * @Description WorkQueue模型System.out.println(" 消费者消费到的消息是 ： " + message);
 * @Date 2023-03-28 22:22
 * @Version 1.0
 */
@Component
public class ConsumerWorkQueue {


    @RabbitListener(queues = "work")
    public void receive1(String message) {
        System.out.println("WorkQueue，消费者1消费到的消息是：" + message);
    }

    @RabbitListener(queues = "work")
    public void receive2(String message) {
        System.out.println("WorkQueue，消费者2消费到的消息是：" + message);
    }
}
