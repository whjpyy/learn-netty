package com.chen.learn.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 把数据写入到文件中
 */
public class WriteToFile {

    public static void main(String[] args) throws IOException {
        String str = "hello,尚硅谷";
        FileOutputStream fis = new FileOutputStream("nio1.txt");

        FileChannel channel = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());

        buffer.flip();

        channel.write(buffer);
        channel.close();
    }
}
