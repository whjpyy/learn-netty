package com.chen.learn.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 拷贝文件
 */
public class CopyFile {
    public static void main(String[] args) throws IOException {

        FileInputStream fis = new FileInputStream("nio1.txt");
        FileChannel fisChannel = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("nio2.txt");
        FileChannel fosChannel = fos.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true){
            // 清空buffer
            byteBuffer.clear();
            int read = fisChannel.read(byteBuffer);
            System.out.println("read: " + read);
            if(read < byteBuffer.capacity()){
                break;
            }
            // 读写反转
            byteBuffer.flip();
            fosChannel.write(byteBuffer);
        }
        fisChannel.close();
        fosChannel.close();
    }
}
