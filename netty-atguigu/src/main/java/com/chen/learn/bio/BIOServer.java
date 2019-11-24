package com.chen.learn.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {

    public static void main(String[] args) throws IOException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);

        while (true){
            System.out.println("等待连接中...");
            Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            threadPool.execute(() -> {
                handler(socket);
            });
        }
    }

    public static void handler(Socket socket){
        try{
            System.out.println("线程 id=" + Thread.currentThread().getId() + ", name=" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true){
                System.out.println("等待客户端数据传输中...");
                int read = inputStream.read(bytes);
                if(read == -1){
                    break;
                }
                // 输出客户端的数据
                System.out.println(Thread.currentThread().getName() + "传输的数据为：" + new String(bytes, 0, read));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
