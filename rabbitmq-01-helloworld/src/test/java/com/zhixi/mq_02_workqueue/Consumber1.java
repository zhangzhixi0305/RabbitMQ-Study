package com.zhixi.mq_02_workqueue;

import com.rabbitmq.client.*;
import com.zhixi.util.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Consumber1
 * @Author zhangzhixi
 * @Description
 * @Date 2023-03-27 18:08
 * @Version 1.0
 */
@SuppressWarnings("all")
public class Consumber1 {
    public static void main(String[] args) throws IOException {
        // 1、创建连接工厂
        Connection connection = RabbitMQUtils.getConnection();
        // 2、创建通道
        final Channel channel = connection.createChannel();
        channel.queueDeclare("work", true, false, false, null);
        channel.basicQos(1);//一次只接受一条未确认的消息
        // 4、消费消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("消费者1，消费消息：" + message);
                channel.basicAck(envelope.getDeliveryTag(), false);//手动确认消息
            }
        };
        // 5、消费消息(关闭自动提交)
        channel.basicConsume("work", false, consumer);
    }
}
