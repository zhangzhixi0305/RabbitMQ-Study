package com.zhixi.rabbitmq_04_routing.topic;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName ConsumerTopic
 * @Author zhangzhixi
 * @Description
 * @Date 2023-03-28 22:52
 * @Version 1.0
 */
@Component
public class ConsumerTopic {

    @RabbitListener(
            bindings = {
                    // 绑定交换机和队列
                    @QueueBinding(
                            // 不指定队列名称，默认创建临时队列
                            value = @Queue,
                            // 指定交换机名称以及交换机类型
                            exchange = @Exchange(value = "topics", type = "topic"),
                            // 设置路由key
                            key = {
                                    "user.*"
                            }
                    )
            }
    )
    public void receive1(String message) {
        System.out.println("message1：" + message);
    }

    @RabbitListener(
            // 绑定交换机和队列
            bindings = @QueueBinding(
                    // 不指定队列名称，默认创建临时队列
                    value = @Queue,
                    // 指定交换机名称以及交换机类型
                    exchange = @Exchange(value = "topics", type = "topic"),
                    // 设置路由key
                    key = {
                            "#.user.#"
                    }
            ))
    public void receive2(String message) {
        System.out.println("message2：" + message);
    }
}
