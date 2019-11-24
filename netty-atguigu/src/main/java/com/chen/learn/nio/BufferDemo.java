package com.chen.learn.nio;

import java.nio.IntBuffer;

public class BufferDemo {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        // Exception in thread "main" java.nio.BufferOverflowException
//        intBuffer.put(11);
        // 将buffer读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

    }
}
