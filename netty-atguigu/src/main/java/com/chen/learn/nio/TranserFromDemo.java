package com.chen.learn.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TranserFromDemo {

    public static void main(String[] args) throws IOException {
        FileChannel fiChannel = new FileInputStream("a.jpg").getChannel();
        FileChannel fosChannel = new FileOutputStream("b.jpg").getChannel();

        fosChannel.transferFrom(fiChannel, 0, fiChannel.size());

        fiChannel.close();
        fosChannel.close();
    }
}
