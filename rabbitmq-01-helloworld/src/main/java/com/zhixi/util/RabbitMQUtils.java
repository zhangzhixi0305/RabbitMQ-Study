package com.zhixi.util;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@SuppressWarnings("all")
public class RabbitMQUtils {

    private static final ConnectionFactory connectionFactory;

    //重量级资源，类加载执行之执行一次
    static {
        // 1、创建连接mq的连接工厂对象
        connectionFactory = new ConnectionFactory();
        // 2、设置连接rabbitmq主机
        connectionFactory.setHost("192.168.31.73");
        // 3、设置端口号
        connectionFactory.setPort(5672);
        // 4、设置用户名和密码
        connectionFactory.setUsername("zhixi");
        connectionFactory.setPassword("zhixi158");
        // 5、设置虚拟主机
        connectionFactory.setVirtualHost("/ems");

    }

    /**
     * 定义提供连接对象的方法
     *
     * @return Connection
     */
    public static Connection getConnection() {
        try {
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭通道和关闭连接工具方法
     *
     * @param channel 通道
     * @param conn    连接
     */
    public static void closeConnectionAndChanel(Channel channel, Connection conn) {
        try {
            if (channel != null) channel.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
