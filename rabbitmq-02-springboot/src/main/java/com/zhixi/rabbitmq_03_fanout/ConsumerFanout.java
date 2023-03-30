package com.zhixi.rabbitmq_03_fanout;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName ConsumerFanout
 * @Author zhangzhixi
 * @Description 路由模式
 * @Date 2023-03-28 22:32
 * @Version 1.0
 */
@Component
public class ConsumerFanout {

    @RabbitListener(
            // 绑定交换机和队列
            bindings = @QueueBinding(
                    // 不指定队列名称，默认创建临时队列
                    value = @Queue,
                    // 指定交换机名称以及交换机类型
                    exchange = @Exchange(value = "logs", type = "fanout")
            ))
    public void receive1(String message) {
        System.out.println("Fanout模式，消费者1消费到的消息是：" + message);
    }

    @RabbitListener(
            // 绑定交换机和队列
            bindings = @QueueBinding(
                    // 不指定队列名称，默认创建临时队列
                    value = @Queue,
                    // 指定交换机名称以及交换机类型
                    exchange = @Exchange(value = "logs", type = "fanout")
            ))
    public void receive2(String message) {
        System.out.println("Fanout模式，消费者2消费到的消息是：" + message);
    }
}
