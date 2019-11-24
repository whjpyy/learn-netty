package com.chen.learn.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 群聊服务端
 */
public class GroupChatServer {

    // 定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    // 构造器
    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞
            listenChannel.configureBlocking(false);
            // 将listenChannel注册到Selector, 关注连接事件
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                int count = selector.select(2000);
                // 有事件发生
                if(count > 0){
                    // 遍历得到selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        // 监听accept
                        if(key.isAcceptable()){
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            // 注册到selector中，关注读事件
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            // 上线提示
                            System.out.println(socketChannel.getRemoteAddress() + " 上线");
                        }
                        // 监听读事件
                        if(key.isReadable()){
                            // 读数据
                            readData(key);
                        }
                        // 当前的key删除，防止重复处理
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取客户端消息
     * @param key
     */
    private void readData(SelectionKey key){
        // 定义一个SocketChannel
        SocketChannel channel = null;
        try{
            channel = (SocketChannel) key.channel();
            // 创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 读取到buffer中
            int read = channel.read(buffer);
            if(read > 0){
                // 把缓冲区的数据转成字符串
                String msg = new String(buffer.array(), 0, read);
                // 输出该消息
                System.out.println("from 客户端：" + msg);
                // 向其他的客户端转发消息
                sendInfoToOtherClients(msg, channel);
            }

        }catch (Exception e){
            try {
                System.out.println(channel.getRemoteAddress() + " 离线");
                // 取消注册
                key.cancel();
                // 关闭通过
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 转发消息给其他客户(通道)
     * @param msg
     * @param self
     */
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            // 排除自己
            if(targetChannel instanceof SocketChannel && targetChannel != self){
                // 转型
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        // 创建一个服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
