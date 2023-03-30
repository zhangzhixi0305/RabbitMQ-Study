package com.zhixi.mq_03_fanout;

import com.rabbitmq.client.*;
import com.zhixi.util.RabbitMQUtils;

import java.io.IOException;

/**
 * @ClassName ConsumberFanout1
 * @Author zhangzhixi
 * @Description
 * @Date 2023-03-28 10:33
 * @Version 1.0
 */
@SuppressWarnings("all")
public class ConsumberFanout1 {
    public static void main(String[] args) throws IOException {
        // 1、创建连接工厂
        Connection connection = RabbitMQUtils.getConnection();
        // 2、创建通道
        assert connection != null;
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs", "fanout");

        // 3、获取临时队列名称
        String queueName = channel.queueDeclare().getQueue();
        // 4、绑定交换机和队列
        // 队列名称、交换机名称、路由键
        channel.queueBind(queueName, "logs", "");
        // 5、消费消息
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1：" + new String(body));
            }
        });
    }
}
