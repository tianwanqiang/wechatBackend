package com.twq.service.base;

import java.io.*;

public class ReadFile {

    public static void main(String[] args) throws IOException {
        File f1 = new File("33.txt");
        if (!f1.exists()) {
            f1.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(f1);
        byte[] buffer = new byte[1024];
        BufferedReader bufferedReader = new BufferedReader(new FileReader("E:\\csbackend\\cs-service\\src\\main\\resources\\22"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("E:\\csbackend\\cs-service\\src\\main\\resources\\33"));
        do {
            String s = bufferedReader.readLine();
            if (s != null && s.length() > 0) {
                String newStr = s.substring(0, s.indexOf("-"));
                if (!newStr.startsWith("0")) {
                    bufferedWriter.write(newStr + "\r\n");
                } else {
                    continue;
                }

            }
        } while (bufferedReader.read() != -1);
        bufferedReader.close();
        bufferedWriter.close();
    }
}
