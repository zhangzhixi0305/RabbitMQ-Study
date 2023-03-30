package com.zhixi.mq_01_helloqueue;

import com.rabbitmq.client.*;
import com.zhixi.util.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName ConsumerMessage
 * @Author zhangzhixi
 * @Description
 * @Date 2023-03-27 13:14
 * @Version 1.0
 */
@SuppressWarnings("all")
public class ConsumerMessage {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtils.getConnection();
        // 2、创建通道
        Channel channel = connection.createChannel();
        // 3、通道绑定对应消息队列
        channel.queueDeclare("hello", true, false, false, null);
        // 4、消费消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("消费消息：" + message);
            }
        };
        // 5、消费消息（自动提交，消息拿到了，不管这边有没有消费完毕，MQ那边的消息已经没有了）
        channel.basicConsume("hello", true, consumer);
        // channel.close();
        // connection.close();
    }
}
