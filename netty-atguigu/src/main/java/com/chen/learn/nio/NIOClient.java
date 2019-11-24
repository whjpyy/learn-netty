package com.chen.learn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

    public static void main(String[] args) throws IOException {
        // 得到一个网络通道SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞模式
        socketChannel.configureBlocking(false);
        // 提供服务器端的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // 连接服务器
        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他的事情");
            }
        }
        // 连接成功，发送数据
        String str = "hello, 尚硅谷~";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        // 将buffer数据写入到channel中
        socketChannel.write(buffer);
        System.in.read();
    }
}
