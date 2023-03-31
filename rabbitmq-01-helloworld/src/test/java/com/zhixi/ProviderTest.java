package com.zhixi;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.zhixi.util.RabbitMQUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Provider
 * @Author zhangzhixi
 * @Description
 * @Date 2023-03-24 18:20
 * @Version 1.0
 */
@SuppressWarnings("all")
public class ProviderTest {

    /**
     * 生产消息-HelloWorld
     */
    @Test
    public void testSenMessage() throws IOException, TimeoutException {
        // 1、创建连接工厂
        Connection connection = RabbitMQUtils.getConnection();
        // 2、创建通道
        assert connection != null;
        Channel channel = connection.createChannel();
        // 3、通道绑定对应消息队列
        //'参数1':用来声明通道对应的队列
        //'参数2':用来指定是否持久化队列
        //'参数3':用来指定是否独占队列,true：独占 false：不独占
        //'参数4':用来指定是否在消费者消费完成后自动删除队列，true：自动删除 false：不自动删除
        //'参数5':对队列的额外配置
        channel.queueDeclare("hello", true, false, false, null);
        // 4、发布消息
        // 参数1：交换机名称
        // 参数2：路由键
        // 参数3：消息的其他属性 - 路由标头等。MessageProperties.PERSISTENT_TEXT_PLAIN：设置消息的持久化
        // 参数3：消息体
        //channel.basicPublish("", "hello", null, "hello rabbitmq".getBytes());
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello rabbitmq".getBytes());
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }

    /**
     * 生产消息-WorkQueue
     */
    @Test
    public void testWorkQueue() throws IOException {
        // 1、创建连接工厂
        Connection connection = RabbitMQUtils.getConnection();
        // 2、创建通道
        Channel channel = connection.createChannel();
        channel.queueDeclare("work", true, false, false, null);
        for (int i = 1; i <= 10; i++) {
            channel.basicPublish("", "work", null, (i + "-hello rabbitmq").getBytes());
        }
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }

    /**
     * 生产消息-扇出/广播
     */
    @Test
    public void testFanout() throws IOException {
        // 1、获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 2、创建通道
        assert connection != null;
        Channel channel = connection.createChannel();
        // 3、设置交换机的名称与交换机的类型。
        // 交换机不存在会自动创建，fanout表示广播。
        channel.exchangeDeclare("logs", "fanout");
        // 4、发送消息
        // 交换机名称、路由键、消息持久化、消息体
        channel.basicPublish("logs", "", null, "fanout type message".getBytes());
        // 5、释放资源
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }

    /**
     * 生产消息-路由-直连
     */
    @Test
    public void testRoutingDirect() throws IOException {
        // 1、获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 2、创建通道
        assert connection != null;
        Channel channel = connection.createChannel();

        // 3、通过通道声明交换机，设置交换机的名称与交换机的类型。
        // 交换机不存在会自动创建，fanout表示广播。
        channel.exchangeDeclare("logs_direct", "direct");
        // 4、路由key
        String routingKey = "error";
        // 5、发送消息
        // 交换机名称、路由键、持久化、消息体
        channel.basicPublish("logs_direct", routingKey, null, ("基于路由方式的直连消息发送，路由键是【" + routingKey + "】发送的消息").getBytes());
        // 5、释放资源
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }

    /**
     * 生产消息-路由-动态路由
     */
    @Test
    public void testRoutingTopic() throws IOException {
        // 1、获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        // 2、创建通道
        assert connection != null;
        Channel channel = connection.createChannel();

        // 3、通过通道声明交换机，设置交换机的名称与交换机的类型。
        // 交换机不存在会自动创建，fanout表示广播。
        channel.exchangeDeclare("topics", "topic");
        // 4、路由key
        String routingKey = "user.save.message";
        // 5、发送消息
        // 交换机名称、路由键、持久化、消息体
        channel.basicPublish("topics", routingKey, null, ("基于路由方式的动态路由消息发送，路由键是【" + routingKey + "】发送的消息").getBytes());
        // 5、释放资源
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }


}
