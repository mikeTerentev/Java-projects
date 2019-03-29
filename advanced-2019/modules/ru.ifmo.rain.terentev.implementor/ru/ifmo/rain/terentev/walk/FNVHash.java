package ru.ifmo.rain.terentev.walk;

import java.util.stream.IntStream;

class FNVHash {

    private final static int B_CONST = 0xff;

    private final static int  FNV_32_PRIME = 0x01000193;

    private int hval = 0x811c9dc5;

   void update(byte[] buff, int amount){
       IntStream.range(0, amount).forEachOrdered(i -> hval = (hval * FNV_32_PRIME) ^ (buff[i] & B_CONST));
    }

    int getHash(){
       return  hval;
    }
}
