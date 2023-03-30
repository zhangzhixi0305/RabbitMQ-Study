package com.zhixi.rabbitmq_04_routing.direct;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName ConsumerRoute
 * @Author zhangzhixi
 * @Description
 * @Date 2023-03-28 22:41
 * @Version 1.0
 */
@Component
public class ConsumerRoute {

    @RabbitListener(
            // 绑定交换机和队列
            bindings = @QueueBinding(
                    // 不指定队列名称，默认创建临时队列
                    value = @Queue,
                    // 指定交换机名称以及交换机类型
                    exchange = @Exchange(value = "logs_direct", type = "direct"),
                    // 设置路由key
                    key = {
                            "info", "warning"
                    }
            ))
    public void receive1(String message) {

        System.out.println("Route模式，消费者1消费到的消息是：" + message);
    }

    @RabbitListener(
            // 绑定交换机和队列
            bindings = @QueueBinding(
                    // 不指定队列名称，默认创建临时队列
                    value = @Queue,
                    // 指定交换机名称以及交换机类型
                    exchange = @Exchange(value = "logs_direct", type = "direct"),
                    // 设置路由key
                    key = {
                            "error"
                    }
            ))
    public void receive2(String message) {

        System.out.println("Route模式，消费者2消费到的消息是：" + message);
    }
}
