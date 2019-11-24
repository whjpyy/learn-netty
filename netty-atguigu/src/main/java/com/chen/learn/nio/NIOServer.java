package com.chen.learn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 得到一个selector对象
        Selector selector = Selector.open();

        // 绑定一个端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把serverSocketChannel注册到selector 关心的事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("注册后的selectionKey的数量： " + selector.keys().size());

        while (true){
            if(selector.select(1000) == 0){ // 没有事件发生
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            // 如果返回的大于0，就获取到相关的selectionKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历Set<SelectionKey>，使用迭代器遍历
            Iterator<SelectionKey> it = selectionKeys.iterator();
            while (it.hasNext()){
                // 获取到selectionKey
                SelectionKey selectionKey = it.next();
                // 根据key对应的通道发生的事件做相应处理
                if(selectionKey.isAcceptable()){ // 如果是OP_ACCEPT,有新的客户端连接
                    // 给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成了一个SocketChannel：" + socketChannel.hashCode());
                    // 将SocketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    // 将当前的SocketChannel注册到selector,关注事件为读，同时给channel关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接后，注册的selectionKey数量=" + selector.keys().size());
                }
                if(selectionKey.isReadable()){ // 发生了OP_READ
                    // 通过key获取对应的channel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    // 获取到该channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    int read = channel.read(byteBuffer);
                    System.out.println("from 客户端：" + new String(byteBuffer.array(),0, read));
                }
                // 手动从集合中移除当前的selectionKey，防止重复操作
                it.remove();
            }
        }
    }
}
