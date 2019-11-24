package com.chen.learn.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 1.可让文件直接在内存(堆外内存)修改，操作系统不需要拷贝一次
 */
public class MappedByteBufferDemo {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("map.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * mode: 模式，使用的是读写模式
         * position：可以直接修改的初始位置
         * size: 映射到内存的大小，即将map.txt的多少个字节映射到内存
         * 可以直接修改的范围为0-5
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, (byte)'H');
        map.put(3, (byte)'9');
        map.put(4, (byte)21);

        System.out.println("修改成功");
        randomAccessFile.close();
    }
}
