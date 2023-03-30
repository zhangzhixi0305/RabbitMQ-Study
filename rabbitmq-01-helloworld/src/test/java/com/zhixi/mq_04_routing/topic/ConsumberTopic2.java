package com.zhixi.mq_04_routing.topic;

import com.rabbitmq.client.*;
import com.zhixi.util.RabbitMQUtils;

import java.io.IOException;

/**
 * @ClassName ConsumberTopic1
 * @Author zhangzhixi
 * @Description
 * @Date 2023-03-28 12:44
 * @Version 1.0
 */
public class ConsumberTopic2 {
    /**
     * 交换机名称
     */
    private static final String exchange = "topics";

    public static void main(String[] args) throws IOException {
        // 1、创建连接工厂
        Connection connection = RabbitMQUtils.getConnection();
        // 2、创建通道
        assert connection != null;
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchange, "topic");
        // 3、获取临时队列名称
        String queueName = channel.queueDeclare().getQueue();
        // 4、绑定交换机和队列
        // 队列名称、交换机名称、路由键
        // user.后面包含0个或者多个单词
        channel.queueBind(queueName, exchange, "user.#");
        // 5、消费消息
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("动态路由中，消费者2中的消息：" + new String(body));
            }
        });
    }
}
