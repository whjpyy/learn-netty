package com.chen.learn.nio;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadFile {

    public static void main(String[] args) throws IOException {
        File file = new File("nio1.txt");

        FileInputStream fis = new FileInputStream(file);
        FileChannel channel = fis.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
        channel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));

        channel.close();
    }
}
