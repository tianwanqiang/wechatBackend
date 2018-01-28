package com.twq.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Hashtable;

public class Serial {
    private static final Hashtable serials = new Hashtable();
    private static final Hashtable fchannels = new Hashtable();

    public Serial() {
    }

    public static long next() {
        return next("");
    }
    public synchronized static long next(String app) {
        try {
            String appKey;
            FileChannel fc;
            MappedByteBuffer serial;
            appKey = (new StringBuilder()).append(app).append(".serial").toString();
            fc = (FileChannel) fchannels.get(appKey);
            serial = (MappedByteBuffer) serials.get(appKey);
            long serno;
            if (serial == null) {
                RandomAccessFile RAFile = new RandomAccessFile(appKey, "rw");
                if (RAFile.length() < 8L)
                    RAFile.writeLong(0L);
                fc = RAFile.getChannel();
                int size = (int) fc.size();
                serial = fc.map(java.nio.channels.FileChannel.MapMode.READ_WRITE, 0L,size);
                fchannels.put(appKey, fc);
                serials.put(appKey, serial);
            }
            FileLock flock = fc.lock();
            serial.rewind();
            serno = serial.getLong();
            serno++;
            serial.flip();
            serial.putLong(serno);
            flock.release();
            return serno;
        } catch (IOException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static void main(String args[]) throws IOException {
        for (int c = 10; c-- > 0;) {
            System.out.println(next("busi"));
            System.out.println(next("controller"));
        }
    }
}