package com.chen.learn.nio;

import java.nio.ByteBuffer;

public class NIOByteBufferPutGet {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putInt(100);
        byteBuffer.putLong(9);
        byteBuffer.putChar('尚');
        byteBuffer.putShort((short)4);

        // 读写反转
        byteBuffer.flip();

        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        // java.nio.BufferUnderflowException
        System.out.println(byteBuffer.getLong());

    }
}
