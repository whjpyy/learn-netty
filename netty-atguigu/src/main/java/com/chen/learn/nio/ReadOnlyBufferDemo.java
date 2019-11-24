package com.chen.learn.nio;

import java.nio.ByteBuffer;

public class ReadOnlyBufferDemo {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte)i);
        }
        // 读取翻转
        byteBuffer.flip();
        // 得到一个只读Buffer
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
    // java.nio.ReadOnlyBufferException
        readOnlyBuffer.put((byte)1);
    }
}
