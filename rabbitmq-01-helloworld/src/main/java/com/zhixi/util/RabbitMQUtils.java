package com.zhixi.util;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@SuppressWarnings("all")
public class RabbitMQUtils {

    private static final ConnectionFactory connectionFactory;

    static {
        //重量级资源  类加载执行之执行一次
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("182.92.209.212");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("zhixi");
        connectionFactory.setPassword("zhixi158");
        connectionFactory.setVirtualHost("/ems");

    }

    //定义提供连接对象的方法
    public static Connection getConnection() {
        try {
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //关闭通道和关闭连接工具方法
    public static void closeConnectionAndChanel(Channel channel, Connection conn) {
        try {
            if (channel != null) channel.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
